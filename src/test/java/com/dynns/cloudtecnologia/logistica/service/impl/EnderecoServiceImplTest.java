package com.dynns.cloudtecnologia.logistica.service.impl;

import com.dynns.cloudtecnologia.logistica.model.entity.Endereco;
import com.dynns.cloudtecnologia.logistica.model.repository.EnderecoRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnderecoServiceImplTest {


    private static final Logger LOG = LoggerFactory.getLogger(EnderecoServiceImplTest.class);

    private static final String MSG_ENDERECO_NOTFOUND = "404 NOT_FOUND \"Endereco não encontrado com ID: ";

    @InjectMocks
    private EnderecoServiceImpl enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Test
    @DisplayName("Deve Salvar Endereço com sucesso.")
    @Order(1)
    void salvarComSucesso() {
        Endereco enderecoMock = getEndereco();

        when(enderecoRepository.save(any())).thenReturn(enderecoMock);
        Endereco enderecoSalvo = enderecoService.salvar(enderecoMock);

        LOG.info(enderecoSalvo.toString());
        assertNotNull(enderecoSalvo);
    }

    @Test
    @DisplayName("Deve editar Endereço com sucesso.")
    @Order(2)
    void atualizarEndereco() {

        Long idEndereco = 1L;
        Endereco enderecoUpdateMock = getEndereco();
        Optional<Endereco> optionEndereco = Optional.of(enderecoUpdateMock);

        when(enderecoRepository.findById(any())).thenReturn(optionEndereco);
        when(enderecoRepository.save(any())).thenReturn(enderecoUpdateMock);

        assertDoesNotThrow(() ->
                enderecoService.atualizarEndereco(idEndereco, enderecoUpdateMock)
        );
    }


    @Test
    @DisplayName("Não Deve editar Endereço - NotFound.")
    @Order(3)
    void atualizarEnderecoNotFound() {

        Long idEnderecoErr = 99L;
        Endereco enderecoUpdateMockErro = getEndereco();

        when(enderecoRepository.findById(any())).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_ENDERECO_NOTFOUND + id);
        });

        String msnRetorno = assertThrows(ResponseStatusException.class, () ->
                enderecoService.atualizarEndereco(idEnderecoErr, enderecoUpdateMockErro))
                .getMessage();

        LOG.info("msnRetorno " + msnRetorno);

    }

    @Test
    @DisplayName("Deve excluir com sucesso")
    @Order(4)
    void deletarEnderecoSucesso() {

        when(enderecoRepository.findById(any())).thenReturn(Optional.of(new Endereco()));
        when(enderecoRepository.save(any())).thenReturn(null);

        assertDoesNotThrow(() ->
                enderecoService.deletarEndereco(1l)
        );

    }

    @Test
    void obterEnderecoViaCep() {
    }


    private Endereco getEndereco() {
        Endereco endereco = new Endereco();
        endereco.setLongitude("-16,265644");
        endereco.setLatitude("-15,95494");
        endereco.setUf("GO");
        endereco.setCep("74915456");
        endereco.setBairro("Vila aparis");
        endereco.setLocalidade("Morrinhos");
        endereco.setLogradouro("Rua acacia 256");

        return endereco;
    }

}