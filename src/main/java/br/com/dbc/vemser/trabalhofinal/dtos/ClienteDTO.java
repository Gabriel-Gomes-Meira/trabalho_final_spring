package br.com.dbc.vemser.trabalhofinal.dtos;

import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Integer idCliente;
    private UsuarioDTO usuarioDTO;
    private ConvenioDTO convenioDTO;
}
