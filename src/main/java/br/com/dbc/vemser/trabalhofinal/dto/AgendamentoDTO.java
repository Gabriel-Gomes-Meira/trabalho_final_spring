package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AgendamentoDTO extends AgendamentoCreateDTO {

    @Schema(description = "Id do agendamento")
    private Integer idAgendamento;

}
