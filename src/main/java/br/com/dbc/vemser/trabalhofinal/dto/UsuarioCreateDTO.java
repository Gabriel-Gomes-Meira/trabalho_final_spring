package br.com.dbc.vemser.trabalhofinal.dto;

import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UsuarioCreateDTO {
    @NotBlank
    @Size(min = 11, max = 11)
    @Schema(description = "CPF", example = "12345678911", required = true)
    private String cpf;
    @NotBlank
    @Size(max = 300)
    @Schema(description = "Email", example = "fulano.silva@gmail.com", required = true)
    private String email;
    @NotBlank
    @Size(max = 255)
    @Schema(description = "Nome", example = "Fulano da Silva", required = true)
    private String nome;
    @NotBlank
    @Size(max = 300)
    @Schema(description = "Senha", example = "123senha123", required = true)
    private String senha;
    @NotBlank
    @Size(max = 600)
    @Schema(description = "Endereço", example = "Av. Getúlio Vargas, 45", required = true)
    private String endereco;
    @NotNull
    @Size(max = 3)
    @Schema(description = "Número de telefone ou celular", example = "84261850")
    private String[] contatos;
    @NotNull
    @Schema(description = "Indentificar se o usuário é um médico ou um cliente", example = "MEDICO", required = true)
    private TipoUsuario tipoUsuario;
}
