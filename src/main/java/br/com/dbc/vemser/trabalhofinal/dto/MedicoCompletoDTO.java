package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MedicoCompletoDTO extends MedicoCreateDTO{

    @Schema(description = "Id do Medico", example = "1", required = true)
    private Integer idMedico;

    @Schema(description = "Objeto usuarioDTO", required = true)
    private UsuarioDTO usuario;

    @Schema(description = "Objeto especialidadeDTO", required = true)
    private EspecialidadeDTO especialidade;
}
