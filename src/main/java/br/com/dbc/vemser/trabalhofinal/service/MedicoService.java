package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dtos.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Medico;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;

import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.MedicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;

public class MedicoService{
    private final MedicoRepository medicoRepository;
    private final ObjectMapper objectMapper;

    public MedicoService(MedicoRepository medicoRepository, ObjectMapper objectMapper) {
        this.medicoRepository = medicoRepository;
        this.objectMapper = objectMapper;
    }

    public MedicoDTO adicionar(MedicoCreateDTO medico) throws RegraDeNegocioException {
        try {
            Medico medicioAdicionar = objectMapper.convertValue(medico, Medico.class);
            Medico medicoAdicionado = medicoRepository.adicionar(medicioAdicionar);
            return objectMapper.convertValue(medicoAdicionado, MedicoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Médico não adicionado por problema no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Médico não adicionado.");
        }
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        try {
            getMedico(id);
            medicoRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Médico não removido por problema no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Médico não removido.");
        }
    }

    public MedicoDTO editar(Integer id, MedicoCreateDTO medico) throws RegraDeNegocioException {
        try {
            getMedico(id);
            Medico medicoEditar = objectMapper.convertValue(medico, Medico.class);
            Medico medicoEditado = medicoRepository.editar(id, medicoEditar);
            return objectMapper.convertValue(medicoEditado, MedicoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Médico não editado por problema no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Médico não editado.");
        }
    }

    public List<MedicoDTO> listar() throws RegraDeNegocioException {
        try {
            return medicoRepository.listar().stream().map(medico -> objectMapper.convertValue(medico, MedicoDTO.class)).toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Médicos não listados por problema no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Médicos não listados.");
        }
    }

    public HashMap<String,String> mostrarInformacoesMedicoUsuario(UsuarioDTO usuarioAtivo) throws RegraDeNegocioException {
        try {
            return medicoRepository.mostrarInformacoesMedicoUsuario(objectMapper.convertValue(usuarioAtivo, Usuario.class));
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Informações do médico não mostradas por problema no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Informações do médico não mostradas");
        }
    }

    public Medico getMedico(Integer id) throws RegraDeNegocioException {
        try{
            return medicoRepository.listar()
                    .stream()
                    .filter(medico -> medico.getIdMedico().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Médico não encontrado!"));
        }catch(BancoDeDadosException e){
            throw new RegraDeNegocioException("Médico não encontrado por problema no banco de dados.");
        }
    }

    public MedicoDTO getMedicoDTO(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getMedico(id), MedicoDTO.class);
    }

}
