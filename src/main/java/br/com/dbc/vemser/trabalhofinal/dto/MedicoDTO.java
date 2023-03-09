package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicoDTO extends MedicoCreateDTO{

    @Schema(description = "Id do Medico", example = "1", required = true)
    private Integer idMedico;

    @Schema(description = "Objeto usuarioDTO", required = true)
    private UsuarioDTO usuarioDTO;

    @Schema(description = "Objeto especialidadeDTO", required = true)
    private EspecialidadeDTO especialidadeDTO;
}
