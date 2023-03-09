package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConvenioDTO extends ConvenioCreateDTO{

    @Schema(description = "Id do Convenio", example = "1", required = true)
    private Integer idConvenio;

}
