package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController implements InterfaceDocumentacao<UsuarioDTO, UsuarioCreateDTO, Integer> {

    private final UsuarioService usuarioService;


    @Override
    public ResponseEntity<List<UsuarioDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UsuarioDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getById(id), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<UsuarioDTO> create(UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.adicionar(usuario), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UsuarioDTO> update(Integer id, UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.editar(id, usuario), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        usuarioService.remover(id);
        return ResponseEntity.ok().build();
    }

}
