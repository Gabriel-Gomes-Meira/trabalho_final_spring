package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.MedicoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/medico")
@RestController
@RequiredArgsConstructor
public class MedicoController implements InterfaceDocumentacao<MedicoDTO, MedicoCreateDTO, Integer>{

    private final MedicoService medicoService;


    @Override
    public ResponseEntity<List<MedicoDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.listarMedicosUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/{id}") // GET localhost:8080/medico/{id}
    public ResponseEntity<MedicoDTO> list(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.mostrarInformacoesMedicoUsuario(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoDTO> create( MedicoCreateDTO medico) throws RegraDeNegocioException {
        log.info("Criando médico...");
        MedicoDTO medicoCriado = medicoService.adicionar(medico);
        log.info("Médico criado!");
        return new ResponseEntity<>(medicoCriado, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoDTO> update(Integer id, MedicoCreateDTO medico) throws RegraDeNegocioException {
        log.info("Atualizando médico...");
        MedicoDTO medicoAtualizado = medicoService.editar(id, medico);
        log.info("Médico atualizado!");
        return new ResponseEntity<>(medicoAtualizado, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        log.info("Deletando médico...");
        medicoService.remover(id);
        log.info("Médico deletado!");
        return ResponseEntity.ok().build();
    }

}
