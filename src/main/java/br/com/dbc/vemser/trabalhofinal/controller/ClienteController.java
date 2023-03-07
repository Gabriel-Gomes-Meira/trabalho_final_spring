package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dtos.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.ClienteDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> mostrarinfoClienteUsuario(@PathVariable Integer idCliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.mostrarInformacoesClienteUsuario(idCliente), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> adicionar(@RequestBody @Valid ClienteCreateDTO cliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.adicionar(cliente), HttpStatus.OK);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> editar (@PathVariable Integer idCliente,
                                              @RequestBody @Valid ClienteCreateDTO cliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.editar(idCliente, cliente), HttpStatus.OK);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> remover(@PathVariable Integer idCliente) throws RegraDeNegocioException {
        clienteService.remover(idCliente);
        return ResponseEntity.ok().build();
    }



}
