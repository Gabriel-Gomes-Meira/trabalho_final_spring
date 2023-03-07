package br.com.dbc.vemser.trabalhofinal.dtos;

import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ClienteDTO {
    private Integer idCliente;
    private UsuarioDTO usuarioDTO;
    private ConvenioDTO convenioDTO;
}
