package br.com.dbc.vemser.trabalhofinal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ClienteDTO extends ClienteCreateDTO {
    private Integer idCliente;
}
