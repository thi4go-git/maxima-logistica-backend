package com.dynns.cloudtecnologia.logistica.service;

import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOCreate;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOUpdate;
import java.util.Optional;

public interface ClienteService {
    Cliente salvar(ClienteDTOCreate clienteDTOCreate);

    Optional<Cliente> buscarPeloCnpjOptional(String cnpj);

    Cliente buscarPeloCnpj(String cnpj);

    Cliente atualizarCliente(ClienteDTOUpdate clienteDTOUpdate, String cnpj);
}
