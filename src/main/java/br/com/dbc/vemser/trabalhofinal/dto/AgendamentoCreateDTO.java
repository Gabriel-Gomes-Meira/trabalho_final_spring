package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoCreateDTO {

    @Schema(description = "Id do cliente", example = "1", required = true)
    private Integer idCliente;
    @Schema(description = "Id do médico", example = "2", required = true)
    private Integer idMedico;
    @Schema(description = "Tratamento a ser seguido pelo cliente", example = "Dipirona de 6 em 6 horas", required = true)
    private String tratamento;
    @Schema(description = "Exame(s) pedidos pelo médico", example = "Sangue e urina", required = true)
    private String exame;
    @Schema(description = "Data e horário da consulta", example = "25/08/2023 15:00", required = true)
    private LocalDateTime dataHorario;


}
