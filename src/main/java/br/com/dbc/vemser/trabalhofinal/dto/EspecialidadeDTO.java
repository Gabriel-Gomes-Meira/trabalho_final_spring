package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EspecialidadeDTO extends EspecialidadeCreateDTO{

    @Schema(description = "Id da especialidade", example = "1", required = true)
    private Integer idEspecialidade;
}
