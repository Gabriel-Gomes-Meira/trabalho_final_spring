package br.com.dbc.vemser.trabalhofinal.controller;


import br.com.dbc.vemser.trabalhofinal.dtos.EspecialidadeCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.EspecialidadeDTO;
import br.com.dbc.vemser.trabalhofinal.service.EspecialidadeService;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/especialidade")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    public EspecialidadeController(EspecialidadeService especialidadeService){
        this.especialidadeService = especialidadeService;
    }

    @GetMapping // GET localhost:8080/especialidades
    public ResponseEntity<List<EspecialidadeDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(especialidadeService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{idEspecialidade}") // GET localhost:8080/especialidade/{idEspecialidade}
    public ResponseEntity<EspecialidadeDTO> listByEndereco(@PathVariable("idEspecialidade") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(especialidadeService.getEspecialidadeDTO(id), HttpStatus.OK);
    }

    @PostMapping // POST localhost:8080/especialidade
    public ResponseEntity<EspecialidadeDTO> create(@Valid @RequestBody EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
        log.info("Criando especialidade...");
        EspecialidadeDTO especialidadeCriada = especialidadeService.adicionar(especialidade);
        log.info("Especialidade criada!");
        return new ResponseEntity<>(especialidadeCriada, HttpStatus.OK);
    }

    @PutMapping("/{idEspecialidade}") // PUT localhost:8080/especialidade/{idEspecialidade}
    public ResponseEntity<EspecialidadeDTO> update(@PathVariable("idEspecialidade") Integer id, @Valid @RequestBody EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
        log.info("Atualizando especialidade...");
        EspecialidadeDTO especialidadeAtualizada = especialidadeService.editar(id, especialidade);
        log.info("Especialidade atualizada!");
        return new ResponseEntity<>(especialidadeAtualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{idEspecialidade}") // DELETE localhost:8080/especialidade/{idEspecialidade}
    public ResponseEntity<Void> delete(@PathVariable("idEspecialidade") Integer id) throws RegraDeNegocioException {
        log.info("Deletando especialidade...");
        especialidadeService.remover(id);
        log.info("Especialidade deletada!");
        return ResponseEntity.ok().build();
    }

}
