package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/cliente")
public class ClienteController implements InterfaceDocumentacao<ClienteCompletoDTO, ClienteCreateDTO, Integer> {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Listar todos os registros", description = "Lista todos os registros sem incluir as informações da tabela usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Todos os registros foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/simple")
    public ResponseEntity<List<ClienteDTO>> listAllSimple() throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.listar(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ClienteCompletoDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.listarFull(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ClienteCompletoDTO> getById(@PathVariable Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.getById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteCompletoDTO> create(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.adicionar(cliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteCompletoDTO> update(Integer idCliente, ClienteCreateDTO cliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.editar(idCliente, cliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer idCliente) throws RegraDeNegocioException {
        clienteService.remover(idCliente);
        return ResponseEntity.ok().build();
    }


}
