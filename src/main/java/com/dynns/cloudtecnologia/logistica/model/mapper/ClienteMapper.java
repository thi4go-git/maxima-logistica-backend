package com.dynns.cloudtecnologia.logistica.model.mapper;


import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOCreate;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOResource;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOResourceList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ClienteMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    EnderecoMapper enderecoMapper;

    public Cliente clienteDTOCreateToCliente(ClienteDTOCreate clienteDTOCreate) {
        return modelMapper.map(clienteDTOCreate, Cliente.class);
    }

    public ClienteDTOResourceList clienteToClienteDTOResourceList(Cliente cliente) {
        ClienteDTOResourceList dto = modelMapper.map(cliente, ClienteDTOResourceList.class);
        dto.setCep(cliente.getEndereco().getCep());
        dto.setLogradouro(cliente.getEndereco().getLogradouro());
        dto.setBairro(cliente.getEndereco().getBairro());
        dto.setLocalidade(cliente.getEndereco().getLocalidade());
        dto.setUf(cliente.getEndereco().getUf());
        dto.setLatitude(cliente.getEndereco().getLatitude());
        dto.setLongitude(cliente.getEndereco().getLongitude());

        return dto;
    }

    public ClienteDTOResource clienteToClienteDTOResource(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTOResource.class);
    }


}
