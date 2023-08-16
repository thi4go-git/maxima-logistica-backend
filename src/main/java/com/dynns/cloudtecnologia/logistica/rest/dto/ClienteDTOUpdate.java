package com.dynns.cloudtecnologia.logistica.rest.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ClienteDTOUpdate {


    @NotBlank(message = "O nome é Obrigatório")
    private String nome;

    @NotBlank(message = "O cep é Obrigatório")
    @Size(min = 8, max = 8, message = "O cep deve ter 8 caracteres.")
    @Pattern(regexp = "\\d{8}", message = "O cep deve conter apenas números.")
    private String cep;

    @NotBlank(message = "O logradouro é Obrigatório")
    private String logradouro;

    @NotBlank(message = "O bairro é Obrigatório")
    private String bairro;

    @NotBlank(message = "A localidade é Obrigatória")
    private String localidade;

    @NotBlank(message = "A UF é Obrigatória")
    @Size(min = 2, max = 2, message = "A UF 2 deve conter 2 caracteres.")
    @Pattern(regexp = "[A-Za-z]{2}", message = "A UF deve conter apenas letras.")
    private String uf;

    @NotBlank(message = "A latitude é Obrigatória")
    private String latitude;

    @NotBlank(message = "A longitude é Obrigatória")
    private String longitude;

}
