package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Convenio {
    private Integer idConvenio;
    private String cadastroOragaoRegulador;
    private Double taxaAbatimento;

}
