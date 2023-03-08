package br.com.dbc.vemser.trabalhofinal.controller;


import br.com.dbc.vemser.trabalhofinal.dtos.ConvenioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.ConvenioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/convenio")
@RequiredArgsConstructor
public class ConvenioController {

    private final ConvenioService convenioService;

    @GetMapping
    public ResponseEntity<List<ConvenioDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(convenioService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConvenioDTO> create(@RequestBody @Valid ConvenioCreateDTO convenio) throws RegraDeNegocioException {
        return new ResponseEntity<>(convenioService.adicionar(convenio), HttpStatus.OK);
    }

    @PutMapping("/{idConvenio}")
    public ResponseEntity<ConvenioDTO> update(@PathVariable Integer idConvenio,
                                              @RequestBody @Valid ConvenioCreateDTO convenio) throws RegraDeNegocioException {
        return new ResponseEntity<>(convenioService.editar(idConvenio, convenio), HttpStatus.OK);
    }

    @DeleteMapping("/{idConvenio}")
    public ResponseEntity<Void> delete(@PathVariable Integer idConvenio) throws RegraDeNegocioException {
        convenioService.remover(idConvenio);
        return ResponseEntity.ok().build();
    }
}
