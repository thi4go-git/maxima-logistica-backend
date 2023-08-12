package com.dynns.cloudtecnologia.logistica.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClienteDTOResource {
    private Long id;
    private String nome;
    private String cnpj;
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
