package br.com.dbc.vemser.trabalhofinal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ClienteCreateDTO {

    @Min(value = 1)
    private Integer idUsuario;
    
    private Integer idConvenio;

}
