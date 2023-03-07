package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Medico {

    private Integer idMedico;
    private String crm;
    private Integer idEspecialidade;
    private Integer idUsuario;

}
