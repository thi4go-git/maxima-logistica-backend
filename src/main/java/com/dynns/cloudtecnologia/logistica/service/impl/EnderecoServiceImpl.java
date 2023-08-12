package com.dynns.cloudtecnologia.logistica.service.impl;

import com.dynns.cloudtecnologia.logistica.model.entity.Endereco;
import com.dynns.cloudtecnologia.logistica.model.repository.EnderecoRepository;
import com.dynns.cloudtecnologia.logistica.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

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
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereco não encontrado com ID: " + idEndereco));
    }
}
