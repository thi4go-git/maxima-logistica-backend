package com.dynns.cloudtecnologia.logistica.service;

import com.dynns.cloudtecnologia.logistica.model.entity.Endereco;
import com.dynns.cloudtecnologia.logistica.rest.dto.EnderecoDTOViacep;


public interface EnderecoService {
    Endereco salvar(Endereco endereco);
    void atualizarEndereco (Long id, Endereco enderecoAtualizado);

    void deletarEndereco (Long id);

    EnderecoDTOViacep obterEnderecoViaCep(String cep);
}
