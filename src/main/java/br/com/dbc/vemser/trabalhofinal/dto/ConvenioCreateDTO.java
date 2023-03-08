package br.com.dbc.vemser.trabalhofinal.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ConvenioCreateDTO {

    @NotBlank
    @Size(max = 40)
    private String cadastroOragaoRegulador;

    @DecimalMax("100.0")
    @DecimalMin("0.0")
    private Double taxaAbatimento;

}
