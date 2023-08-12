package com.dynns.cloudtecnologia.logistica.service;

import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOCreate;

public interface ClienteService {
    Cliente salvar(ClienteDTOCreate clienteDTOCreate);
}
