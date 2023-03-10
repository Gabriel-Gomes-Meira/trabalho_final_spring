package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Agendamento {

    private Integer idAgendamento, idCliente,idMedico;
    private String tratamento, exame;
    private LocalDateTime dataHorario;

}
