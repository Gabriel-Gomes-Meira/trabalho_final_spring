package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ConvenioCreateDTO {

    @NotBlank
    @Size(max = 40)
    @Schema(description = "Cadastro do orgão regulador do convênio", example = "6489418", required = true)
    private String cadastroOragaoRegulador;

    @DecimalMax("100.0")
    @DecimalMin("0.0")
    @Schema(description = "Taxa de abatimento que incide sobre o valor da especialidade", example = "10.00", required = true)
    private Double taxaAbatimento;

}
