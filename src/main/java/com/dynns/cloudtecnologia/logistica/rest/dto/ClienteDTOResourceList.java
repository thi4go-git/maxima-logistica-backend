package com.dynns.cloudtecnologia.logistica.rest.dto;

import lombok.Data;


@Data
public class ClienteDTOResourceList {
    private Long id;
    private String nome;
    private String cnpj;
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String latitude;
    private String longitude;
}
