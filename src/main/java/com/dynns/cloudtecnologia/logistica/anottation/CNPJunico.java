package com.dynns.cloudtecnologia.logistica.anottation;

import com.dynns.cloudtecnologia.logistica.anottation.impl.CNPJunicoImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CNPJunicoImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CNPJunico {
    String message() default "Já existe um Cliente cadastrado com o CNPJ informado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
