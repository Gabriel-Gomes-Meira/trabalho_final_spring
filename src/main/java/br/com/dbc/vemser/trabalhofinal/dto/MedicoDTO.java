package br.com.dbc.vemser.trabalhofinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicoDTO extends MedicoCreateDTO{
    private Integer idMedico;
    private UsuarioDTO usuarioDTO;
    private EspecialidadeDTO especialidadeDTO;
}
