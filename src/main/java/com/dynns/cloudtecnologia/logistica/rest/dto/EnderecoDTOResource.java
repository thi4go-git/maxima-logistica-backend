package com.dynns.cloudtecnologia.logistica.rest.dto;


import lombok.Data;

@Data
public class EnderecoDTOResource {
    private Long id;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
    private String latitude;
    private String longitude;
}
