package br.com.dbc.vemser.trabalhofinal.controller;


import br.com.dbc.vemser.trabalhofinal.dto.ConvenioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.ConvenioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/convenio")
public class ConvenioController implements InterfaceDocumentacao<ConvenioDTO, ConvenioCreateDTO, Integer> {

    private final ConvenioService convenioService;

    @Override
    public ResponseEntity<List<ConvenioDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(convenioService.listar(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ConvenioDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(convenioService.getById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ConvenioDTO> create(ConvenioCreateDTO convenio) throws RegraDeNegocioException {
        return new ResponseEntity<>(convenioService.adicionar(convenio), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ConvenioDTO> update(Integer id, ConvenioCreateDTO convenio) throws RegraDeNegocioException {
        return new ResponseEntity<>(convenioService.editar(id, convenio), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        convenioService.remover(id);
        return ResponseEntity.ok().build();
    }
}

