package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AgendamentoDadosDTO extends AgendamentoDTO{

    @Schema(description = "Objeto cliente do agendamento")
    private ClienteDTO clienteDTO;
    @Schema(description = "Objeto médico do agendamento")
    private MedicoDTO medicoDTO;

}
