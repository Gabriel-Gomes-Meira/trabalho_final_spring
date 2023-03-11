package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDadosDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController  implements  InterfaceDocumentacao<AgendamentoDTO, AgendamentoCreateDTO, Integer> {

    private final AgendamentoService agendamentoService;
    @Override
    public ResponseEntity<List<AgendamentoDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.listar(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AgendamentoDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.getAgendamentoDTO(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AgendamentoDTO> create(AgendamentoCreateDTO agendamento) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.adicionar(agendamento), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AgendamentoDTO> update(Integer id, AgendamentoCreateDTO agendamento) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.editar(id, agendamento), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        agendamentoService.remover(id);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Lista todos os agendamentos que um médico possui", description = "Listar todos os agendamentos que um médico possui passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Todos os registros foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idMedico}/medico")
    public ResponseEntity<List<AgendamentoDadosDTO>> listByMedico(@PathVariable Integer idMedico) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.listarPorMedico(idMedico), HttpStatus.OK);
    }


    @Operation(summary = "Lista todos os agendamentos que um cliente possui", description = "Listar todos os agendamentos que um cliente possui passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Todos os registros foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCliente}/cliente")
    public ResponseEntity<List<AgendamentoDadosDTO>> listByCliente(@PathVariable Integer idCliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.listarPorCliente(idCliente), HttpStatus.OK);
    }
}
