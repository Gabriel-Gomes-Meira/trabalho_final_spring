package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medico {
    private Integer idMedico;
    private Integer idUsuario;
    private Integer idEspecialidade;
    private String crm;
}
