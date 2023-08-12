package com.dynns.cloudtecnologia.logistica.rest.resource;

import com.dynns.cloudtecnologia.logistica.rest.dto.EnderecoDTOViacep;
import com.dynns.cloudtecnologia.logistica.service.impl.EnderecoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("api/enderecos")
public class EnderecoResource {


    @Autowired
    private EnderecoServiceImpl enderecoService;

    @GetMapping("/viacep/{cep}")
    public ResponseEntity<EnderecoDTOViacep> retornarEnderecoViaCep(
            @PathVariable("cep") @NotBlank(message = "cep é obrigatório!") final String cep
    ) {
        EnderecoDTOViacep endereco = enderecoService.obterEnderecoViaCep(cep);
        return ResponseEntity.ok().body(endereco);
    }

}
