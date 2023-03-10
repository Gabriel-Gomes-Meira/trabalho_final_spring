package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/cliente")
public class ClienteController implements InterfaceDocumentacao<ClienteDTO, ClienteCreateDTO, Integer> {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public ResponseEntity<List<ClienteDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.listar(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ClienteDTO> getById(@PathVariable Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.getById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteDTO> create(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.adicionar(cliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteDTO> update(Integer idCliente, ClienteCreateDTO cliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.editar(idCliente, cliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer idCliente) throws RegraDeNegocioException {
        clienteService.remover(idCliente);
        return ResponseEntity.ok().build();
    }


}
