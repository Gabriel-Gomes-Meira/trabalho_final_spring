package br.com.dbc.vemser.trabalhofinal.dtos;


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
    private String crm;
    @NotNull
    private Integer idEspecialidade;
    @NotNull
    private Integer idUsuario;


}
