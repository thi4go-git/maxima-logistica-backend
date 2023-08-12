package com.dynns.cloudtecnologia.logistica.model.mapper;

import com.dynns.cloudtecnologia.logistica.exception.GeralException;
import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOCreate;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOResource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente clienteDTOCreateToCliente(ClienteDTOCreate clienteDTOCreate) {
        ModelMapper modelMapper = new ModelMapper();
        Cliente cliente = null;
        try {
            cliente = modelMapper.map(clienteDTOCreate, Cliente.class);
        } catch (Exception exception) {
            throw new GeralException("Erro ao mapear Cliente do objeto ClienteDTOCreate.");
        }
        return cliente;
    }

    public ClienteDTOResource clienteToClienteDTOResource(Cliente cliente) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cliente, ClienteDTOResource.class);
    }
}
