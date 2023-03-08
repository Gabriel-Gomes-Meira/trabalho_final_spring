package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dtos.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.MedicoService;
import br.com.dbc.vemser.trabalhofinal.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequestMapping("/medico")
@RestController
public class MedicoController {

    private final MedicoService medicoService;
    private final UsuarioService usuarioService;

    public MedicoController(MedicoService medicoService, UsuarioService usuarioService){
        this.medicoService = medicoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping // GET localhost:8080/medico
    public ResponseEntity<List<MedicoDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.listarMedicosUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/{idMedico}") // GET localhost:8080/medico/{idMedico}
    public ResponseEntity<MedicoDTO> list(@PathVariable("idMedico") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.mostrarInformacoesMedicoUsuario(id), HttpStatus.OK);
    }

    @PostMapping // POST localhost:8080/medico
    public ResponseEntity<MedicoDTO> create(@Valid @RequestBody MedicoCreateDTO medico) throws RegraDeNegocioException {
        log.info("Criando médico...");
        MedicoDTO medicoCriado = medicoService.adicionar(medico);
        log.info("Médico criado!");
        return new ResponseEntity<>(medicoCriado, HttpStatus.OK);
    }

    @PutMapping("/{idMedico}") // PUT localhost:8080/medico/{idMedico}
    public ResponseEntity<MedicoDTO> update(@PathVariable("idMedico") Integer id, @Valid @RequestBody MedicoCreateDTO medico) throws RegraDeNegocioException {
        log.info("Atualizando médico...");
        MedicoDTO medicoAtualizado = medicoService.editar(id, medico);
        log.info("Médico atualizado!");
        return new ResponseEntity<>(medicoAtualizado, HttpStatus.OK);
    }
    @DeleteMapping("/{idMedico}") // DELETE localhost:8080/medico/{idMedico}
    public ResponseEntity<Void> delete(@PathVariable("idMedico") Integer id) throws RegraDeNegocioException {
        log.info("Deletando médico...");
        medicoService.remover(id);
        log.info("Médico deletado!");
        return ResponseEntity.ok().build();
    }

}
