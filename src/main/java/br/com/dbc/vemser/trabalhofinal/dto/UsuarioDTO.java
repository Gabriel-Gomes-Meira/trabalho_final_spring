package br.com.dbc.vemser.trabalhofinal.dto;

import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioDTO extends UsuarioCreateDTO {
    private Integer idUsuario;
    private String cpf, email, nome, endereco;
    private String[] contatos;
    private TipoUsuario tipoUsuario;
}
