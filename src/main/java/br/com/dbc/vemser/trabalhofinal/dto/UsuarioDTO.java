package br.com.dbc.vemser.trabalhofinal.dto;

import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioDTO extends UsuarioCreateDTO {
    @Schema(description = "Id do Usuário", example = "1", required = true)
    private Integer idUsuario;
}
