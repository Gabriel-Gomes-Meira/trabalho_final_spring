package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnderecoDTO {

    @Schema(description = "CEP", example = "0505")
    private String cep;
    @Schema(description = "Logradouro", example = "Rua Getúlio Vargas")
    private String logradouro;
    @Schema(description = "Complemento", example = "lado ímpar")
    private String complemento;
    @Schema(description = "Bairro", example = "Centro")
    private String bairro;
    @Schema(description = "Cidade", example = "São Paulo")
    private String localidade;
    @Schema(description = "Estado", example = "SP")
    private String uf;

}
