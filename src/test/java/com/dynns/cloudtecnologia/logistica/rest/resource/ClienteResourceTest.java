package com.dynns.cloudtecnologia.logistica.rest.resource;

import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOCreate;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOResourceList;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOUpdate;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class ClienteResourceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }


    private static final Logger LOG = LoggerFactory.getLogger(ClienteResourceTest.class);

    private static final String URL_PATH = "/api/clientes";
    private static final String CNPJ_VALIDO = "12345678901234";
    private static final String NOME_VALIDO = "Aparecidinho Eletro";


    @Test
    @DisplayName("Deve salvar um Cliente com campos preenchidos")
    @Order(1)
    void salvarComSucesso() throws JsonProcessingException {

        ClienteDTOCreate dtoCreate = getClienteDTOCreate();

        var resposta = given()
                .contentType(ContentType.JSON)
                .body(dtoCreate)
                .when()
                .post(URL_PATH)
                .then()
                .extract()
                .response();

        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);

        assertNotNull(responseBody);

        assertEquals(HttpStatus.SC_CREATED, resposta.statusCode());
        assertEquals(dtoCreate.getNome(), resposta.jsonPath().getString("nome"));
        assertEquals(dtoCreate.getCnpj(), resposta.jsonPath().getString("cnpj"));
        assertEquals(dtoCreate.getCep(), resposta.jsonPath().getString("cep"));
        assertEquals(dtoCreate.getLogradouro(), resposta.jsonPath().getString("logradouro"));
        assertEquals(dtoCreate.getBairro(), resposta.jsonPath().getString("bairro"));
        assertEquals(dtoCreate.getLocalidade(), resposta.jsonPath().getString("localidade"));
        assertEquals(dtoCreate.getUf(), resposta.jsonPath().getString("uf"));
        assertEquals(dtoCreate.getLatitude(), resposta.jsonPath().getString("latitude"));
        assertEquals(dtoCreate.getLongitude(), resposta.jsonPath().getString("longitude"));

    }

    @Test
    @DisplayName("Não Deve salvar - CNPJ já cadastrado")
    @Order(2)
    void naoSalvarCnpjJaCadastrado() {

        ClienteDTOCreate dtoCreate = getClienteDTOCreate();

        var resposta = given()
                .contentType(ContentType.JSON)
                .body(dtoCreate)
                .when()
                .post(URL_PATH)
                .then()
                .extract()
                .response();

        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);

        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_BAD_REQUEST, resposta.statusCode());

        assertTrue(responseBody.contains("Já existe um Cliente cadastrado com o CNPJ informado"));

    }

    @Test
    @DisplayName("Não Deve salvar - Sem nome, endereço,cep e coordenadas")
    @Order(3)
    void naoSalvarSemInformacoes() {

        ClienteDTOCreate dtoCreate = new ClienteDTOCreate();

        var resposta = given()
                .contentType(ContentType.JSON)
                .body(dtoCreate)
                .when()
                .post(URL_PATH)
                .then()
                .extract()
                .response();

        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);

        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_BAD_REQUEST, resposta.statusCode());

        assertTrue(responseBody.contains("O Campo latitude não pode ser nulo."));
        assertTrue(responseBody.contains("O nome deverá ser informado."));
        assertTrue(responseBody.contains("A uf deverá ser informada."));
        assertTrue(responseBody.contains("O bairro deverá ser informado."));
        assertTrue(responseBody.contains("O cep deverá ser informado."));
        assertTrue(responseBody.contains("O Campo longitude não pode ser nulo."));
        assertTrue(responseBody.contains("A localidade deverá ser informada."));
        assertTrue(responseBody.contains("O Campo longitude não pode ser nulo."));
        assertTrue(responseBody.contains("O logradouro deverá ser informado."));
        assertTrue(responseBody.contains("O cnpj deverá ser informado."));

    }

    @Test
    @DisplayName("Deve buscarPeloCnpj")
    @Order(4)
    void buscarPeloCnpj() {


        var resposta = given()
                .contentType(ContentType.JSON)
                .pathParam("cnpj", CNPJ_VALIDO)
                .when()
                .get(URL_PATH + "/{cnpj}")
                .then()
                .extract()
                .response();

        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);

        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_OK, resposta.statusCode());
        assertEquals(NOME_VALIDO, resposta.jsonPath().getString("nome"));
        assertEquals(CNPJ_VALIDO, resposta.jsonPath().getString("cnpj"));
    }

    @Test
    @DisplayName("Não Deve buscarPeloCnpj Inválido")
    @Order(5)
    void naoBuscarPeloCnpjInvalido() {

        String cnpjInvalido = "12345678901235";

        var resposta = given()
                .contentType(ContentType.JSON)
                .pathParam("cnpj", cnpjInvalido)
                .when()
                .get(URL_PATH + "/{cnpj}")
                .then()
                .extract()
                .response();

        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);

        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());

        assertTrue(responseBody.contains("Cliente não encontrado com CNPJ " + cnpjInvalido));

    }

    @Test
    @DisplayName("Deve buscarEnderecoCliente")
    @Order(6)
    void buscarEnderecoCliente() {

        var resposta = given()
                .contentType(ContentType.JSON)
                .pathParam("cnpj", CNPJ_VALIDO)
                .when()
                .get(URL_PATH + "/{cnpj}/endereco")
                .then()
                .extract()
                .response();

        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);

        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_OK, resposta.statusCode());

    }

    @Test
    @DisplayName("Deve atualizarCliente")
    @Order(7)
    void atualizarCliente() {

        ClienteDTOUpdate ClienteDTOUpdate = new ClienteDTOUpdate();
        ClienteDTOUpdate.setBairro("Bairro UPDATE");
        ClienteDTOUpdate.setNome(NOME_VALIDO + " Update");
        ClienteDTOUpdate.setLogradouro("Logrado");
        ClienteDTOUpdate.setLocalidade("Localidade");
        ClienteDTOUpdate.setCep("74913555");
        ClienteDTOUpdate.setLongitude("-15,544545");
        ClienteDTOUpdate.setLatitude("-20,545454");
        ClienteDTOUpdate.setUf("MT");

        var resposta = given()
                .contentType(ContentType.JSON)
                .body(ClienteDTOUpdate)
                .pathParam("cnpj", CNPJ_VALIDO)
                .when()
                .put("/api/clientes/{cnpj}")
                .then()
                .extract()
                .response();

        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);

        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_OK, resposta.statusCode());
        assertEquals(ClienteDTOUpdate.getNome(), resposta.jsonPath().getString("nome"));
        assertEquals(CNPJ_VALIDO, resposta.jsonPath().getString("cnpj"));

    }

    @Test
    @DisplayName("Deve listarTodosPaginadoFilter")
    @Order(8)
    void listarTodosPaginadoFilter() {

        int pagina = 0;
        int tamhnoPagina = 15;

        ClienteDTOResourceList filtoCliente = new ClienteDTOResourceList();
        filtoCliente.setNome("aparecIdinhO eL");

        var resposta = given()
                .contentType(ContentType.JSON)
                .body(filtoCliente)
                .param("page", pagina)
                .param("size", tamhnoPagina)
                .when()
                .get("/api/clientes")
                .then()
                .extract()
                .response();

        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);

        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_OK, resposta.statusCode());

        assertEquals("1", resposta.jsonPath().getString("numberOfElements"));
        assertEquals("15", resposta.jsonPath().getString("size"));

    }

    @Test
    @DisplayName("Deve deletarClientePeloCnpj")
    @Order(9)
    void deletarClientePeloCnpj() {

        var resposta = given()
                .contentType(ContentType.JSON)
                .pathParam("cnpj", CNPJ_VALIDO)
                .when()
                .delete("/api/clientes/{cnpj}")
                .then()
                .extract()
                .response();

        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);

        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_NO_CONTENT, resposta.statusCode());

    }
    @Test
    @DisplayName("Não Deve deletarClientePeloCnpj Not Found")
    @Order(10)
    void naoDeletarClientePeloCnpj() {

        var resposta = given()
                .contentType(ContentType.JSON)
                .pathParam("cnpj", CNPJ_VALIDO+"9")
                .when()
                .delete("/api/clientes/{cnpj}")
                .then()
                .extract()
                .response();

        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);

        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());

    }

    private ClienteDTOCreate getClienteDTOCreate() {
        ClienteDTOCreate dto = new ClienteDTOCreate();
        dto.setUf("UF");
        dto.setCep("74913550");
        dto.setCnpj(CNPJ_VALIDO);
        dto.setBairro("Bairro");
        dto.setLatitude("-16,45646545");
        dto.setLongitude("-19,1514541");
        dto.setNome(NOME_VALIDO);
        dto.setLogradouro("Rua macaba");
        dto.setLocalidade("Campinas");
        return dto;
    }
}