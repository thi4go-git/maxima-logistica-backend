package com.dynns.cloudtecnologia.logistica.service.impl;


import com.dynns.cloudtecnologia.logistica.exception.GeralException;
import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.model.entity.Endereco;
import com.dynns.cloudtecnologia.logistica.model.mapper.ClienteMapper;
import com.dynns.cloudtecnologia.logistica.model.repository.ClienteRepository;
import com.dynns.cloudtecnologia.logistica.rest.client.ViaCepClient;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOCreate;
import com.dynns.cloudtecnologia.logistica.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
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
    private ViaCepClient viaCepClient;


    @Override
    @Transactional
    public Cliente salvar(ClienteDTOCreate clienteDTOCreate) {

        Endereco enderecoViaCep = null;
        try {
            enderecoViaCep = viaCepClient.obterEndereco(clienteDTOCreate.getCep()).getBody();
            enderecoViaCep.setCep(enderecoViaCep.getCep().replace("-", ""));
            enderecoViaCep.setLatitude(clienteDTOCreate.getLatitude());
            enderecoViaCep.setLongitude(clienteDTOCreate.getLongitude());
        } catch (Exception exception) {
            throw new GeralException("Erro ao obter endereço VIA CEP.");
        }

        Endereco enderecoSalvo = enderecoService.salvar(enderecoViaCep);

        Cliente clienteMapeado = clienteMapper.clienteDTOCreateToCliente(clienteDTOCreate);
        clienteMapeado.setEndereco(enderecoSalvo);

        return clienteRepository.save(clienteMapeado);
    }

    @Override
    public Optional<Cliente> buscarPeloCnpjOptional(String cnpj) {
        return clienteRepository.findByCnpj(cnpj);
    }

    @Override
    public Cliente buscarPeloId(Long id) {
        return clienteRepository.
                findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado com ID " + id));
    }

}
