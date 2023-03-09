package br.com.dbc.vemser.trabalhofinal.entity;

import br.com.dbc.vemser.trabalhofinal.dto.MedicoCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Medico extends MedicoCreateDTO {

    @Schema(description = "Id do médico", example = "1", required = true)
    private Integer idMedico;


}
