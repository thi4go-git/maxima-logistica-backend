package com.dynns.cloudtecnologia.logistica.rest.client;

import com.dynns.cloudtecnologia.logistica.model.entity.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br", name = "ViaCepClient")
public interface ViaCepClient {

    @GetMapping(value = "/ws/{cep}/json/")
    ResponseEntity<Endereco> obterEndereco(@PathVariable("cep") String cep);

}
