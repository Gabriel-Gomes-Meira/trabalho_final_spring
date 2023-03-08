package br.com.dbc.vemser.trabalhofinal.dto;

import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UsuarioCreateDTO {
    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;
    @NotBlank
    @Size(max = 300)
    private String email;
    @NotBlank
    @Size(max = 255)
    private String nome;
    @NotBlank
    @Size(max = 300)
    private String senha;
    @NotBlank
    @Size(max = 600)
    private String endereco;
    @NotNull
    @Size(max = 3)
    private String[] contatos;
    @NotNull
    private TipoUsuario tipoUsuario;
}
