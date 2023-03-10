package br.com.dbc.vemser.trabalhofinal.dto;

import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UsuarioDTO {
    @Schema(description = "Id do Usuário", example = "1")
    private Integer idUsuario;
    @Schema(description = "CPF", example = "12345678911")
    private String cpf;
    @Schema(description = "Email", example = "fulano.silva@gmail.com")
    private String email;
    @Schema(description = "Nome", example = "Fulano da Silva")
    private String nome;
    @Schema(description = "Cep do endereço", example = "12345678")
    private String cep;
    @Schema(description = "Número do endereço", example = "44545454")
    private String numero;
    @Schema(description = "Número de telefone ou celular", example = "84261850")
    private String[] contatos;
    @Schema(description = "Indentificar se o usuário é um médico ou um cliente", example = "MEDICO")
    private TipoUsuario tipoUsuario;
}
