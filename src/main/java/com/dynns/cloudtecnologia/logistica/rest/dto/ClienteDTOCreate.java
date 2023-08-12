package com.dynns.cloudtecnologia.logistica.rest.dto;

import com.dynns.cloudtecnologia.logistica.anottation.CNPJunico;
import lombok.Data;

import javax.validation.constraints.*;


@Data
public class ClienteDTOCreate {

    @NotBlank(message = "O nome deve ser informado.")
    private String nome;

    @NotBlank(message = "O cnpj deve ser informado.")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 caracteres.")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter apenas números.")
    @CNPJunico
    private String cnpj;

    @NotBlank(message = "O cep deve ser informado.")
    @Size(min = 8, max = 8, message = "O cep deve ter 8 caracteres.")
    @Pattern(regexp = "\\d{8}", message = "O cep deve conter apenas números.")
    private String cep;

    @NotBlank(message = "O Campo latitude não poderá ser nulo.")
    private String latitude;

    @NotBlank(message = "O Campo longitude não poderá ser nulo.")
    private String longitude;
}
