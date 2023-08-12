package com.dynns.cloudtecnologia.logistica.model.mapper;


import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOCreate;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOResource;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOResourceList;
import com.dynns.cloudtecnologia.logistica.rest.dto.EnderecoDTOResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Cliente clienteDTOCreateToCliente(ClienteDTOCreate clienteDTOCreate) {
        return modelMapper.map(clienteDTOCreate, Cliente.class);
    }

    public ClienteDTOResourceList clienteToClienteDTOResourceList(Cliente cliente) {
        ClienteDTOResourceList dto = modelMapper.map(cliente, ClienteDTOResourceList.class);
        dto.setCep(cliente.getEndereco().getCep());
        dto.setLogradouro(cliente.getEndereco().getLogradouro());
        dto.setBairro(cliente.getEndereco().getBairro());
        dto.setLocalidade(cliente.getEndereco().getLocalidade());
        dto.setLatitude(cliente.getEndereco().getLatitude());
        dto.setLongitude(cliente.getEndereco().getLongitude());
        return dto;
    }

    public ClienteDTOResource clienteToClienteDTOResource(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTOResource.class);
    }

    public List<ClienteDTOResourceList> listClienteToListClienteDTOResource(List<Cliente> listCliente) {
        List<ClienteDTOResourceList> clientesDTOResourceList = new ArrayList<>();

        for (Cliente clienteAtual : listCliente) {
            ClienteDTOResourceList novoClienteDtoResourceList = new ClienteDTOResourceList();
            novoClienteDtoResourceList.setId(clienteAtual.getId());
            novoClienteDtoResourceList.setNome(clienteAtual.getNome());
            novoClienteDtoResourceList.setCnpj(clienteAtual.getCnpj());
            novoClienteDtoResourceList.setCep(clienteAtual.getEndereco().getCep());
            novoClienteDtoResourceList.setLogradouro(clienteAtual.getEndereco().getLogradouro());
            novoClienteDtoResourceList.setBairro(clienteAtual.getEndereco().getBairro());
            novoClienteDtoResourceList.setLocalidade(clienteAtual.getEndereco().getLocalidade());
            novoClienteDtoResourceList.setLatitude(clienteAtual.getEndereco().getLatitude());
            novoClienteDtoResourceList.setLongitude(clienteAtual.getEndereco().getLongitude());

            clientesDTOResourceList.add(novoClienteDtoResourceList);
        }

        return clientesDTOResourceList;
    }
}
