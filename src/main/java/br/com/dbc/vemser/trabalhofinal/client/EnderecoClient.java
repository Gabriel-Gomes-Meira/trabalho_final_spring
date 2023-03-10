package br.com.dbc.vemser.trabalhofinal.client;

import br.com.dbc.vemser.trabalhofinal.dto.EnderecoDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value="viacep", url="viacep.com.br/ws")
@Headers("Content-Type: application/json")
public interface EnderecoClient {
    @RequestLine("GET /{cep}/json")
    EnderecoDTO getEndereco(@Param("cep") String cep);

}
