package com.dynns.cloudtecnologia.logistica.model.mapper;

import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.rest.dto.EnderecoDTOResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public EnderecoDTOResource clienteToEnderecoDTOResource(Cliente cliente) {
        return modelMapper.map(cliente.getEndereco(), EnderecoDTOResource.class);
    }
}
