package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgendamentoDadosDTO extends AgendamentoDTO{

    @Schema(description = "Nome do cliente")
    private String cliente;

    @Schema(description = "Nome do Médico")
    private String medico;
}
