package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ClienteCompletoDTO {
    @Schema(description = "Id do cliente", example = "1", required = true)
    private Integer idCliente;
    @Schema(description = "Objeto UsuarioDTO", required = true)
    private UsuarioDTO usuario;
    @Schema(description = "Objeto ConvenioDTO", required = true)
    private ConvenioDTO convenio;

}
