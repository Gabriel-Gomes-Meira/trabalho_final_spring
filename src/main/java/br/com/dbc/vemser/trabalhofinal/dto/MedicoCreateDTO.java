package br.com.dbc.vemser.trabalhofinal.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class MedicoCreateDTO {

    @NotBlank
    @Schema(description = "CRM", example = "CRM/SP 123456", required = true)
    private String crm;
    @NotNull
    @Schema(description = "Id da especialidade", example = "1", required = true)
    private Integer idEspecialidade;
    @NotNull
    @Schema(description = "Id do usuário", example = "1", required = true)
    private Integer idUsuario;


}
