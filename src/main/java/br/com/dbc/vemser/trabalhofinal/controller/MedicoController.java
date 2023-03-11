package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Listar todos os registros", description = "Lista todos os registros sem incluir as informações da tabela usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Todos os registros foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
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
        MedicoCompletoDTO medicoCriado = medicoService.adicionar(medico);
        return new ResponseEntity<>(medicoCriado, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoCompletoDTO> update(Integer id, MedicoCreateDTO medico) throws RegraDeNegocioException {
        MedicoCompletoDTO medicoAtualizado = medicoService.editar(id, medico);
        return new ResponseEntity<>(medicoAtualizado, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        medicoService.remover(id);
        return ResponseEntity.ok().build();
    }

    // <TODO> endpoint listMinimazed

}
