package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dtos.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.adicionar(usuario), HttpStatus.OK);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Integer idUsuario, @Valid @RequestBody UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.editar(idUsuario, usuario), HttpStatus.OK);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> update(@PathVariable Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.remover(idUsuario);
        return ResponseEntity.ok().build();
    }
}
