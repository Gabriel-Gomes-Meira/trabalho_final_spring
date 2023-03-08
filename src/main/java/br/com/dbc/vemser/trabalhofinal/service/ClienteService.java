package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dtos.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.ClienteDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Cliente;
import br.com.dbc.vemser.trabalhofinal.entity.Convenio;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    public ClienteDTO adicionar(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        try {
            Cliente clienteEntity = objectMapper.convertValue(cliente, Cliente.class);
            clienteRepository.adicionar(clienteEntity);
            return objectMapper.convertValue(clienteEntity, ClienteDTO.class);
        } catch (BancoDeDadosException e) {
            log.error("Erro aqui");
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        try {
            verificarSeIdClienteExiste(id);
            clienteRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public ClienteDTO editar(Integer id, ClienteCreateDTO cliente) throws RegraDeNegocioException {
        try {
            Cliente clienteEntity = objectMapper.convertValue(cliente, Cliente.class);
            verificarSeIdClienteExiste(id);
            clienteRepository.editar(id, clienteEntity);
            return objectMapper.convertValue(clienteEntity, ClienteDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public List<ClienteDTO> listar() throws RegraDeNegocioException {
        try {
            return clienteRepository.listarClienteDTOs();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public ClienteDTO mostrarInformacoesClienteUsuario(Integer idCliente) throws RegraDeNegocioException {
        try {
            ClienteDTO clienteDTO = clienteRepository.listarClienteDTOId(idCliente);
            if (clienteDTO != null) {
                return clienteDTO;
            } else {
                throw new RegraDeNegocioException("Cliente não existe!");
            }
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        }
    }

    private Cliente verificarSeIdClienteExiste(Integer id) throws RegraDeNegocioException {
        try {
            return clienteRepository.listar()
                    .stream()
                    .filter(cliente -> cliente.getIdCliente().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado!"));
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }


}
