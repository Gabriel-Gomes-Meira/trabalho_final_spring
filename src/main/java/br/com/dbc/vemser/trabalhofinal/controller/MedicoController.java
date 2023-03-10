package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.MedicoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/medico")
@RestController
@RequiredArgsConstructor
public class MedicoController implements InterfaceDocumentacao<MedicoCompletoDTO, MedicoCreateDTO, Integer> {

    private final MedicoService medicoService;

    @GetMapping("/simple")
    public ResponseEntity<List<MedicoDTO>> listAllSimple() throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.listar(), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<List<MedicoCompletoDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.listarFull(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoCompletoDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.getMedicoDTOById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoCompletoDTO> create(MedicoCreateDTO medico) throws RegraDeNegocioException {
        log.info("Criando médico...");
        MedicoCompletoDTO medicoCriado = medicoService.adicionar(medico);
        log.info("Médico criado!");
        return new ResponseEntity<>(medicoCriado, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoCompletoDTO> update(Integer id, MedicoCreateDTO medico) throws RegraDeNegocioException {
        log.info("Atualizando médico...");
        MedicoCompletoDTO medicoAtualizado = medicoService.editar(id, medico);
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

    // <TODO> endpoint listMinimazed

}
