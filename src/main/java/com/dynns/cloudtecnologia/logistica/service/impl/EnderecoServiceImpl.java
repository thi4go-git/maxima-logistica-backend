package com.dynns.cloudtecnologia.logistica.service.impl;

import com.dynns.cloudtecnologia.logistica.exception.GeralException;
import com.dynns.cloudtecnologia.logistica.model.entity.Endereco;
import com.dynns.cloudtecnologia.logistica.model.repository.EnderecoRepository;
import com.dynns.cloudtecnologia.logistica.rest.client.ViaCepClient;
import com.dynns.cloudtecnologia.logistica.rest.dto.EnderecoDTOViacep;
import com.dynns.cloudtecnologia.logistica.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;


@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepClient viaCepClient;

    @Override
    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    private static final String MSG_ENDERECO_NOTFOUND = "Endereco não encontrado com ID: ";

    @Override
    @Transactional
    public void atualizarEndereco(Long idEndereco, Endereco enderecoAtualizado) {
        enderecoRepository.
                findById(idEndereco)
                .map(enderecoAchado -> {
                    enderecoAchado.setCep(enderecoAtualizado.getCep());
                    enderecoAchado.setLogradouro(enderecoAtualizado.getLogradouro());
                    enderecoAchado.setBairro(enderecoAtualizado.getBairro());
                    enderecoAchado.setLocalidade(enderecoAtualizado.getLocalidade());
                    enderecoAchado.setUf(enderecoAtualizado.getUf());
                    enderecoAchado.setLatitude(enderecoAtualizado.getLatitude());
                    enderecoAchado.setLongitude(enderecoAtualizado.getLongitude());

                    return enderecoRepository.save(enderecoAchado);
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_ENDERECO_NOTFOUND + idEndereco));
    }

    @Override
    @Transactional
    public void deletarEndereco(Long idEndereco) {

        Endereco enderecoDeletar = enderecoRepository.
                findById(idEndereco)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_ENDERECO_NOTFOUND + idEndereco));

        enderecoRepository.delete(enderecoDeletar);

    }

    @Override
    public EnderecoDTOViacep obterEnderecoViaCep(String cep) {
        try {
            return viaCepClient.obterEndereco(cep).getBody();
        } catch (Exception e) {
            throw new GeralException("Erro ao obter endereço VIA CEP ");
        }
    }
}
