package br.com.dbc.vemser.trabalhofinal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Integer idCliente;
    private UsuarioDTO usuarioDTO;
    private ConvenioDTO convenioDTO;
}
