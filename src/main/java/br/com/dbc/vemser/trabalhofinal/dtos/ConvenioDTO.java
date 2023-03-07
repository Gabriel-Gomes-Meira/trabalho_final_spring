package br.com.dbc.vemser.trabalhofinal.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ConvenioDTO extends ConvenioCreateDTO{

    private Integer idConvenio;

}
