package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface InterfaceDocumentacao<saida, entrada, id> {

    @Operation(summary = "Listar registros", description = "Lista todos os registros")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Todos os registros foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<saida>> listAll() throws RegraDeNegocioException;


    @Operation(summary = "Criar registro", description = "Cria um registro")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Registro criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<saida> create(@Valid @RequestBody entrada e) throws RegraDeNegocioException;


    @Operation(summary = "Atualizar registro", description = "Atualiza um registro passando o id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<saida> update(@PathVariable id id, @Valid @RequestBody entrada e) throws RegraDeNegocioException;

    @Operation(summary = "Deletar registro", description = "Detela um registro passando o id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Registro deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable id id) throws RegraDeNegocioException;
}
