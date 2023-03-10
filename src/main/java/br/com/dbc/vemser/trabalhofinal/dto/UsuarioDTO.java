package br.com.dbc.vemser.trabalhofinal.dto;

import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UsuarioDTO {
    @Schema(description = "Id do Usuário", example = "1", required = true)
    private Integer idUsuario;
    @Schema(description = "CPF", example = "12345678911", required = true)
    private String cpf;
    @Schema(description = "Email", example = "fulano.silva@gmail.com", required = true)
    private String email;
    @Schema(description = "Nome", example = "Fulano da Silva", required = true)
    private String nome;
    @Schema(description = "Endereço", example = "Av. Getúlio Vargas, 45", required = true)
    private String endereco;
    @Schema(description = "Número de telefone ou celular", example = "84261850")
    private String[] contatos;
    @Schema(description = "Indentificar se o usuário é um médico ou um cliente", example = "MEDICO", required = true)
    private TipoUsuario tipoUsuario;
}
