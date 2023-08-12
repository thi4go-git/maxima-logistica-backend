package com.dynns.cloudtecnologia.logistica.rest.controller;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    @Getter
    private List<String> erros;

    public ApiErrors(List<String> erros) {
        this.erros = erros;
    }

    public ApiErrors(String message) {
        this.erros = Arrays.asList(message);
    }
}
