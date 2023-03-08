package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dtos.EspecialidadeCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.EspecialidadeDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Especialidade;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.EspecialidadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EspecialidadeService {
    private final EspecialidadeRepository especialidadeRepository;
    private final ObjectMapper objectMapper;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository, ObjectMapper objectMapper) {
        this.especialidadeRepository = especialidadeRepository;
        this.objectMapper = objectMapper;
    }

    // criação de um objeto
    public EspecialidadeDTO adicionar(EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
        try {
            Especialidade novaEspecialidade = objectMapper.convertValue(especialidade, Especialidade.class);
            Especialidade especialidadeAdicionada = especialidadeRepository.adicionar(novaEspecialidade);
            return objectMapper.convertValue(especialidadeAdicionada, EspecialidadeDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Especialidade não adicionada por problema no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Especialidade não adicionada.");
        }
    }

    // remoção
    public void remover(Integer id) throws RegraDeNegocioException {
        try {
            getEspecialidade(id);
            especialidadeRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Especialidade não removida por problema no banco de dados.");
        }
    }


    // atualização de um objeto
    public EspecialidadeDTO editar(Integer id, EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
        try {
            getEspecialidade(id);
            Especialidade especialidadeEditar = objectMapper.convertValue(especialidade, Especialidade.class);
            especialidadeRepository.editar(id, especialidadeEditar);
            return getEspecialidadeDTO(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Especialidade não editada por problema no banco de dados.");
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException("Especialidade não editada.");
        }
    }

    // leitura
    public List<EspecialidadeDTO> listar() throws RegraDeNegocioException {
        try {
            return especialidadeRepository.listar().stream().map(especialidade -> objectMapper.convertValue(especialidade, EspecialidadeDTO.class)).toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Especialidade não adicionada por problema no banco de dados.");
        }
    }

    public EspecialidadeDTO getEspecialidadeDTO(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getEspecialidade(id), EspecialidadeDTO.class);
    }

    public Especialidade getEspecialidade(Integer id) throws RegraDeNegocioException {
        try{
            return especialidadeRepository.listar()
                    .stream()
                    .filter(especialidade -> especialidade.getIdEspecialidade().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada!"));
        }catch(BancoDeDadosException e){
            throw new RegraDeNegocioException("Especialidade não encontrado por problema no banco de dados.");
        }
    }


}
