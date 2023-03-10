package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.client.EnderecoClient;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Medico;

import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.MedicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final EnderecoClient enderecoClient;


    public MedicoCompletoDTO adicionar(MedicoCreateDTO medico) throws RegraDeNegocioException {
        try {
            usuarioService.verificarIdUsuario(medico.getIdUsuario());
            Medico medicioAdicionar = objectMapper.convertValue(medico, Medico.class);
            Medico medicoAdicionado = medicoRepository.adicionar(medicioAdicionar);

            return getMedicoDTOById(medicoAdicionado.getIdMedico());
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

    public MedicoCompletoDTO editar(Integer id, MedicoCreateDTO medico) throws RegraDeNegocioException {
        try {
            if (!Objects.equals(getMedico(id).getIdUsuario(), medico.getIdUsuario())) {
                usuarioService.verificarIdUsuario(medico.getIdUsuario());
            }
            Medico medicoEditar = objectMapper.convertValue(medico, Medico.class);
            medicoRepository.editar(id, medicoEditar);
            return getMedicoDTOById(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Médico não editado por problema no banco de dados.");
        }

    }

    public List<MedicoDTO> listar() throws RegraDeNegocioException {
        try {
            return medicoRepository.listar().stream().map(medico ->
                    objectMapper.convertValue(medico, MedicoDTO.class)).toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Médicos não listados por problema no banco de dados.");
        }
    }

    public MedicoCompletoDTO getMedicoDTOById(Integer idMedico) throws RegraDeNegocioException {
        try {
            MedicoCompletoDTO medicoCompletoDTO = medicoRepository.getMedicoDTO(idMedico);
            medicoCompletoDTO.setUsuario(usuarioService.getById(medicoCompletoDTO.getIdUsuario()));
            return medicoCompletoDTO;
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Informações do médico não mostradas por problema no banco de dados.");
        }
    }

    public List<MedicoCompletoDTO> listarFull() throws RegraDeNegocioException {
        try {
            return medicoRepository.listarMedicosUsuariosDTOs().stream().map(medicoCompletoDTO -> {
                medicoCompletoDTO.getUsuario().setEndereco(
                        enderecoClient.getEndereco(
                                medicoCompletoDTO.getUsuario().getCep()
                        )
                );
                return medicoCompletoDTO;
            }).toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Informações do médico não mostradas por problema no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Informações do médico não mostradas");
        }
    }

    public Medico getMedico(Integer id) throws RegraDeNegocioException {
        try {
            return medicoRepository.listar()
                    .stream()
                    .filter(medico -> medico.getIdMedico().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Médico não encontrado!"));
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Médico não encontrado por problema no banco de dados.");
        }
    }

    public MedicoCompletoDTO getMedicoDTO(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getMedico(id), MedicoCompletoDTO.class);
    }

}
