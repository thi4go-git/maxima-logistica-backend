package com.dynns.cloudtecnologia.logistica.service.impl;

import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.model.entity.Endereco;
import com.dynns.cloudtecnologia.logistica.model.mapper.ClienteMapper;
import com.dynns.cloudtecnologia.logistica.model.mapper.EnderecoMapper;
import com.dynns.cloudtecnologia.logistica.model.repository.ClienteRepository;
import com.dynns.cloudtecnologia.logistica.rest.client.ViaCepClient;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOCreate;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOUpdate;
import com.dynns.cloudtecnologia.logistica.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoServiceImpl enderecoService;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private ViaCepClient viaCepClient;


    @Override
    @Transactional
    public Cliente salvar(ClienteDTOCreate clienteDTOCreate) {

        Endereco enderecoSalvo = enderecoService.salvar(enderecoMapper.clienteDTOCreateToEndereco(clienteDTOCreate));

        Cliente clienteMapeado = clienteMapper.clienteDTOCreateToCliente(clienteDTOCreate);
        clienteMapeado.setEndereco(enderecoSalvo);

        return clienteRepository.save(clienteMapeado);
    }

    @Override
    public Optional<Cliente> buscarPeloCnpjOptional(String cnpj) {
        return clienteRepository.findByCnpj(cnpj);
    }

    @Override
    public Cliente buscarPeloCnpj(String cnpj) {
        return clienteRepository.
                findByCnpj(cnpj)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado com CNPJ " + cnpj));

    }

    @Override
    @Transactional
    public Cliente atualizarCliente(ClienteDTOUpdate clienteDTOUpdate, String cnpj) {

        Cliente clienteAchado = clienteRepository.
                findByCnpj(cnpj)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado com CNPJ " + cnpj));

        clienteAchado.setNome(clienteDTOUpdate.getNome());
        clienteAchado.getEndereco().setCep(clienteDTOUpdate.getCep());
        clienteAchado.getEndereco().setLogradouro(clienteDTOUpdate.getLogradouro());
        clienteAchado.getEndereco().setBairro(clienteDTOUpdate.getBairro());
        clienteAchado.getEndereco().setLocalidade(clienteDTOUpdate.getLocalidade());
        clienteAchado.getEndereco().setUf(clienteDTOUpdate.getUf());
        clienteAchado.getEndereco().setLatitude(clienteDTOUpdate.getLatitude());
        clienteAchado.getEndereco().setLongitude(clienteDTOUpdate.getLongitude());

        System.out.println(clienteAchado);

        return clienteAchado;
    }


}
