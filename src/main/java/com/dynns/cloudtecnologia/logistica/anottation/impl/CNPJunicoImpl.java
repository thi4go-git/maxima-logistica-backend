package com.dynns.cloudtecnologia.logistica.anottation.impl;

import com.dynns.cloudtecnologia.logistica.anottation.CNPJunico;
import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.service.impl.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CNPJunicoImpl implements ConstraintValidator<CNPJunico, String> {

    @Autowired
    private ClienteServiceImpl clienteService;

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Cliente> optionalCliente = clienteService.buscarPeloCnpjOptional(cnpj);
        return optionalCliente.isEmpty();
    }
}
