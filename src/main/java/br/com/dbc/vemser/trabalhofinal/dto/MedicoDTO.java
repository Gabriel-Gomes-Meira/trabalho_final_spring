package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MedicoDTO extends MedicoCreateDTO{

    @Schema(description = "Id do Medico", example = "1", required = true)
    private Integer idMedico;
}
