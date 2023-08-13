package com.dynns.cloudtecnologia.logistica.service.impl;

import com.dynns.cloudtecnologia.logistica.model.entity.Endereco;
import com.dynns.cloudtecnologia.logistica.model.repository.EnderecoRepository;
import com.dynns.cloudtecnologia.logistica.rest.client.ViaCepClient;
import com.dynns.cloudtecnologia.logistica.rest.dto.EnderecoDTOViacep;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Mock
    private ViaCepClient viaCepClient;

    private static final String CEP = "74913-330";
    private static final String LOGRADOURO = "Rua teste";
    private static final String COMPLEMENTO = "CASA 1";
    private static final String BAIRRO = "BAIRRO";
    private static final String LOCALIDADE = "LOCALIDADE";
    private static final String IBGE = "IBGE";
    private static final String GIA = "GIA";
    private static final String DDD = "DDD";
    private static final String SIAFI = "SIAFI";


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

        EnderecoDTOViacep viaCep = getEnderecoViaCep();

        ResponseEntity<EnderecoDTOViacep> responseEntity = new ResponseEntity<>(viaCep, HttpStatus.valueOf(500));

        when(viaCepClient.obterEndereco(any())).thenReturn(responseEntity);

        LOG.info(responseEntity.getBody().toString());

        assertDoesNotThrow(() ->
                enderecoService.obterEnderecoViaCep(CEP)
        );

        assertNotNull(responseEntity.getBody());
        assertEquals(CEP, responseEntity.getBody().getCep());
        assertEquals(LOGRADOURO, responseEntity.getBody().getLogradouro());
        assertEquals(COMPLEMENTO, responseEntity.getBody().getComplemento());
        assertEquals(BAIRRO, responseEntity.getBody().getBairro());
        assertEquals(LOCALIDADE, responseEntity.getBody().getLocalidade());
        assertEquals(IBGE, responseEntity.getBody().getIbge());
        assertEquals(GIA, responseEntity.getBody().getGia());
        assertEquals(DDD, responseEntity.getBody().getDdd());
        assertEquals(SIAFI, responseEntity.getBody().getSiafi());


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

    private EnderecoDTOViacep getEnderecoViaCep() {
        EnderecoDTOViacep viaCep = new EnderecoDTOViacep();
        viaCep.setCep(CEP);
        viaCep.setLogradouro(LOGRADOURO);
        viaCep.setComplemento(COMPLEMENTO);
        viaCep.setBairro(BAIRRO);
        viaCep.setLocalidade(LOCALIDADE);
        viaCep.setIbge(IBGE);
        viaCep.setGia(GIA);
        viaCep.setDdd(DDD);
        viaCep.setSiafi(SIAFI);

        return viaCep;
    }
}