package br.com.dbc.vemser.trabalhofinal.controller;


import br.com.dbc.vemser.trabalhofinal.dto.EspecialidadeCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.EspecialidadeDTO;
import br.com.dbc.vemser.trabalhofinal.service.EspecialidadeService;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/especialidade")
public class EspecialidadeController implements InterfaceDocumentacao<EspecialidadeDTO, EspecialidadeCreateDTO, Integer> {

    private final EspecialidadeService especialidadeService;


    @Override // GET localhost:8080/especialidades
    public ResponseEntity<List<EspecialidadeDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(especialidadeService.listar(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EspecialidadeDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(especialidadeService.getById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EspecialidadeDTO> create(EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
        EspecialidadeDTO especialidadeCriada = especialidadeService.adicionar(especialidade);
        return new ResponseEntity<>(especialidadeCriada, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EspecialidadeDTO> update(Integer id, EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
        EspecialidadeDTO especialidadeAtualizada = especialidadeService.editar(id, especialidade);
        return new ResponseEntity<>(especialidadeAtualizada, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        especialidadeService.remover(id);
        return ResponseEntity.ok().build();
    }

}
