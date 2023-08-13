package com.dynns.cloudtecnologia.logistica.rest.dto;

import com.dynns.cloudtecnologia.logistica.anottation.CNPJunico;
import lombok.Data;

import javax.validation.constraints.*;


@Data
public class ClienteDTOCreate {

    @NotBlank(message = "O nome deverá ser informado.")
    private String nome;

    @NotBlank(message = "O cnpj deverá ser informado.")
    @Size(min = 14, max = 14, message = "O CNPJ deverá conter 14 caracteres.")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deverá conter apenas números.")
    @CNPJunico
    private String cnpj;

    @NotBlank(message = "O cep deverá ser informado.")
    @Size(min = 8, max = 8, message = "O cep deve conter 8 caracteres.")
    @Pattern(regexp = "\\d{8}", message = "O cep deverá conter apenas números.")
    private String cep;

    @NotBlank(message = "O logradouro deverá ser informado.")
    private String logradouro;

    @NotBlank(message = "O bairro deverá ser informado.")
    private String bairro;

    @NotBlank(message = "A localidade deverá ser informada.")
    private String localidade;

    @NotBlank(message = "A uf deverá ser informada.")
    @Size(min =2 , max = 2, message = "A uf 2 deverá conter 2 caracteres.")
    private String uf;

    @NotBlank(message = "O Campo latitude não pode ser nulo.")
    private String latitude;

    @NotBlank(message = "O Campo longitude não pode ser nulo.")
    private String longitude;
}
