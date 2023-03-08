package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dtos.ConvenioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Convenio;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ConvenioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ConvenioService {
    private final ConvenioRepository convenioRepository;
    private final ObjectMapper objectMapper;



    public ConvenioDTO adicionar(ConvenioCreateDTO convenio) throws RegraDeNegocioException {
        try {
            Convenio convenioEntity = objectMapper.convertValue(convenio, Convenio.class);
            convenioRepository.adicionar(convenioEntity);
            return objectMapper.convertValue(convenioEntity, ConvenioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        try {
            verificarSeIdConvenioExiste(id);
            convenioRepository.remover2(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }


    public ConvenioDTO editar(Integer id, ConvenioCreateDTO convenio) throws RegraDeNegocioException {
        try {
            Convenio convenioEntity = objectMapper.convertValue(convenio, Convenio.class);
            verificarSeIdConvenioExiste(id);
            convenioRepository.editar(id, convenioEntity);
            return objectMapper.convertValue(convenioEntity, ConvenioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }

    }

    public List<ConvenioDTO> listar() throws RegraDeNegocioException {
        try {
            return convenioRepository.listar()
                    .stream()
                    .map(convenio -> objectMapper.convertValue(convenio, ConvenioDTO.class))
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco!");
        }
    }

    public Convenio verificarSeExiste(Integer id) throws RegraDeNegocioException{
        try {
            return convenioRepository.getUmId(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }


    public Convenio verificarSeIdConvenioExiste(Integer id) throws RegraDeNegocioException {
        try {
            return convenioRepository.listar().stream()
                    .filter(convenio -> convenio.getIdConvenio().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Convenio não encontrado!"));
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

}
