package com.dynns.cloudtecnologia.logistica.rest.resource;

import com.dynns.cloudtecnologia.logistica.rest.dto.EnderecoDTOViacep;
import com.dynns.cloudtecnologia.logistica.service.impl.EnderecoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("api/enderecos")
@CrossOrigin("*")
public class EnderecoResource {


    @Autowired
    private EnderecoServiceImpl enderecoService;

    @GetMapping("/viacep/{cep}")
    @Operation(summary = "Obter endereço completo através do CEP", description = "Este endpoint faz uma consulta de endereço na API VIA CEP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta de endereço realizada"),
            @ApiResponse(responseCode = "500", description = "Erro ao consultar ViaCep")
    })
    public ResponseEntity<EnderecoDTOViacep> retornarEnderecoViaCep(
            @PathVariable("cep") @NotBlank(message = "cep é obrigatório!") final String cep
    ) {
        EnderecoDTOViacep endereco = enderecoService.obterEnderecoViaCep(cep);
        return ResponseEntity.ok().body(endereco);
    }

}
