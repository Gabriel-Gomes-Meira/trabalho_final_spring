package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClienteDTO  extends ClienteCreateDTO{
    @Schema(description = "Identificador do cliente")
    private Integer idCliente;
}
