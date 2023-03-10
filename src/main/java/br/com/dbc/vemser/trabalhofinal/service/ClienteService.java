package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.client.EnderecoClient;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Cliente;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final EnderecoClient enderecoClient;

    public ClienteCompletoDTO adicionar(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        try {
            usuarioService.verificarIdUsuario(cliente.getIdUsuario());
            Cliente clienteEntity = objectMapper.convertValue(cliente, Cliente.class);
            clienteRepository.adicionar(clienteEntity);

            // Retornar Cliente com todos os dados
            return getById(clienteEntity.getIdCliente());
        } catch (BancoDeDadosException e) {
            log.error("Erro ao adicionar um cliente");
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        try {
            getCliente(id);
            clienteRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public ClienteCompletoDTO editar(Integer id, ClienteCreateDTO cliente) throws RegraDeNegocioException {
        try {
            Cliente clienteEntity = objectMapper.convertValue(cliente, Cliente.class);
            if (!Objects.equals(getCliente(id).getIdUsuario(), clienteEntity.getIdUsuario())){
                usuarioService.verificarIdUsuario(clienteEntity.getIdUsuario());
            }
            clienteRepository.editar(id, clienteEntity);
            return getById(clienteEntity.getIdCliente());
         //   return objectMapper.convertValue(clienteEntity, ClienteCompletoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public List<ClienteDTO> listar() throws RegraDeNegocioException {
        try {
            return clienteRepository.listar().stream().map(cliente ->
                    objectMapper.convertValue(cliente, ClienteDTO.class)).toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public List<ClienteCompletoDTO> listarFull() throws RegraDeNegocioException {
        try {
            return clienteRepository.listarClienteDTOs().stream().map(clienteCompletoDTO -> {
                clienteCompletoDTO.getUsuario().setEndereco(
                        enderecoClient.getEndereco(
                                clienteCompletoDTO.getUsuario().getCep()
                        )
                );
                return clienteCompletoDTO;
            }).toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public ClienteCompletoDTO getById(Integer idCliente) throws RegraDeNegocioException {
        try {
            ClienteCompletoDTO clienteCompletoDTO = clienteRepository.getClienteCompletoDTO(idCliente);
            if (clienteCompletoDTO != null) {
                clienteCompletoDTO.setUsuario(usuarioService.getById(clienteCompletoDTO.getUsuario().getIdUsuario()));
                return clienteCompletoDTO;
            } else {
                throw new RegraDeNegocioException("Cliente não existe!");
            }
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente getCliente(Integer id) throws RegraDeNegocioException {
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
