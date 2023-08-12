package com.dynns.cloudtecnologia.logistica.service.impl;

import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.model.entity.Endereco;
import com.dynns.cloudtecnologia.logistica.model.mapper.ClienteMapper;
import com.dynns.cloudtecnologia.logistica.model.mapper.EnderecoMapper;
import com.dynns.cloudtecnologia.logistica.model.repository.ClienteRepository;
import com.dynns.cloudtecnologia.logistica.rest.client.ViaCepClient;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOCreate;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOResourceList;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOUpdate;
import com.dynns.cloudtecnologia.logistica.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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


    private static final String MSG_CLIENTE_NOTFOUND = "Cliente não encontrado com CNPJ ";

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
                        new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_CLIENTE_NOTFOUND + cnpj));

    }

    @Override
    @Transactional
    public Cliente atualizarCliente(ClienteDTOUpdate clienteDTOUpdate, String cnpj) {

        Cliente clienteAtualizar = clienteRepository.
                findByCnpj(cnpj)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_CLIENTE_NOTFOUND + cnpj));

        clienteAtualizar.setNome(clienteDTOUpdate.getNome());
        clienteAtualizar.getEndereco().setCep(clienteDTOUpdate.getCep());
        clienteAtualizar.getEndereco().setLogradouro(clienteDTOUpdate.getLogradouro());
        clienteAtualizar.getEndereco().setBairro(clienteDTOUpdate.getBairro());
        clienteAtualizar.getEndereco().setLocalidade(clienteDTOUpdate.getLocalidade());
        clienteAtualizar.getEndereco().setUf(clienteDTOUpdate.getUf());
        clienteAtualizar.getEndereco().setLatitude(clienteDTOUpdate.getLatitude());
        clienteAtualizar.getEndereco().setLongitude(clienteDTOUpdate.getLongitude());

        return clienteAtualizar;
    }

    @Override
    @Transactional
    public void deletarClientePeloCnpj(String cnpj) {

        Cliente clienteDeletar = clienteRepository.
                findByCnpj(cnpj)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_CLIENTE_NOTFOUND + cnpj));

        Long idEnderecoDeletar = clienteDeletar.getEndereco().getId();
        clienteRepository.delete(clienteDeletar);
        enderecoService.deletarEndereco(idEnderecoDeletar);

    }

    @Override
    public Page<Cliente> listarTodosPageFilter(int page, int size, ClienteDTOResourceList clienteFiltro) {

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);


        Example<Cliente> example = Example.of(clienteMapper.clienteDTOResourceListToCliente(clienteFiltro), matcher);

        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.Direction.ASC,
                "id");

        return clienteRepository.findAll(example, pageRequest);

    }

}
