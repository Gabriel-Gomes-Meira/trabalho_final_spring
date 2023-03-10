package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class AgendamentoCreateDTO {

    @NotNull
    @Schema(description = "Id do cliente", example = "1", required = true)
    private Integer idCliente;
    @NotNull
    @Schema(description = "Id do médico", example = "2", required = true)
    private Integer idMedico;
    @Size(max = 40)
    @Schema(description = "Tratamento a ser seguido pelo cliente", example = "Dipirona de 6 em 6 horas")
    private String tratamento;
    @Size(max = 40)
    @Schema(description = "Exame(s) pedidos pelo médico", example = "Sangue e urina")
    private String exame;
    @NotNull
    @Schema(description = "Data e horário da consulta", example = "25/08/2023 15:00", required = true)
    private LocalDateTime dataHorario;


}
