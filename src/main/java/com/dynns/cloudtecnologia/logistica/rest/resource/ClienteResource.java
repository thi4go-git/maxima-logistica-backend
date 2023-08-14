package com.dynns.cloudtecnologia.logistica.rest.resource;

import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.model.mapper.ClienteMapper;
import com.dynns.cloudtecnologia.logistica.model.mapper.EnderecoMapper;
import com.dynns.cloudtecnologia.logistica.rest.dto.*;
import com.dynns.cloudtecnologia.logistica.service.impl.ClienteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;


@RestController
@RequestMapping("api/clientes")
@CrossOrigin("*")
public class ClienteResource {

    @Autowired
    private ClienteServiceImpl clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private EnderecoMapper enderecoMapper;

    private static final String CLIENTE_NAO_LOCALIZADO = "Cliente não localizado!";
    private static final String SERVER_ERROR = "Erro interno do servidor!";

    @PostMapping
    @Operation(summary = "Criar Cliente", description = "Este endpoint Cria um novo Cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado!"),
            @ApiResponse(responseCode = "500", description = SERVER_ERROR)
    })
    public ResponseEntity<ClienteDTOResourceList> salvar(@RequestBody @Valid ClienteDTOCreate clienteDTOCreate) {
        Cliente salvo = clienteService.salvar(clienteDTOCreate);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(clienteMapper.clienteToClienteDTOResourceList(salvo));
    }


    @GetMapping("/{cnpj}")
    @Operation(summary = "Buscar cliente pelo CNPJ", description = "Este endpoint busca cliente pelo CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente Localizado!"),
            @ApiResponse(responseCode = "404", description = CLIENTE_NAO_LOCALIZADO),
            @ApiResponse(responseCode = "500", description = SERVER_ERROR)
    })
    public ResponseEntity<ClienteDTOResourceList> buscarPeloCnpj(@PathVariable("cnpj") @NotBlank(message = "cnpj é obrigatório!") final String cnpj) {
        Cliente cliente = clienteService.buscarPeloCnpj(cnpj);
        return ResponseEntity.ok().body(clienteMapper.clienteToClienteDTOResourceList(cliente));
    }

    @GetMapping("/{cnpj}/endereco")
    @Operation(summary = "Buscar endereço cliente pelo CNPJ", description = "Este endpoint busca endereço cliente pelo CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada!"),
            @ApiResponse(responseCode = "404", description = CLIENTE_NAO_LOCALIZADO),
            @ApiResponse(responseCode = "500", description = SERVER_ERROR)
    })
    public ResponseEntity<EnderecoDTOResource> buscarEnderecoCliente(@PathVariable("cnpj") @NotBlank(message = "cnpj é obrigatório!") final String cnpj) {
        Cliente cliente = clienteService.buscarPeloCnpj(cnpj);
        return ResponseEntity.ok().body(enderecoMapper.clienteToEnderecoDTOResource(cliente));
    }

    @PutMapping("/{cnpj}")
    @Operation(summary = "Atualizar cliente", description = "Este endpoint Atualiza cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização Concluída!"),
            @ApiResponse(responseCode = "404", description = CLIENTE_NAO_LOCALIZADO),
            @ApiResponse(responseCode = "500", description = SERVER_ERROR)
    })
    public ResponseEntity<ClienteDTOResourceList> atualizarCliente(
            @PathVariable("cnpj") @NotBlank(message = "cnpj é obrigatório!") final String cnpj,
            @Valid @RequestBody final ClienteDTOUpdate clienteDTOUpdate) {

        Cliente clienteAtualizado = clienteService.atualizarCliente(clienteDTOUpdate, cnpj);
        return ResponseEntity.ok().body(clienteMapper.clienteToClienteDTOResourceList(clienteAtualizado));
    }

    @PostMapping("/filter")
    @Operation(summary = "Listar com paginação + Filtros", description = "Este endpoint Lista Paginandodo + Filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = SERVER_ERROR)
    })
    public ResponseEntity<Page<Cliente>> listarTodosPaginadoFilter(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody ClienteDTOResourceList clienteFilter
    ) {

        Page<Cliente> clientePageFilter = clienteService.listarTodosPageFilter(page, size, clienteFilter);

        return ResponseEntity.ok().body(clientePageFilter);
    }

    @DeleteMapping("/{cnpj}")
    @Operation(summary = "Deletar Cliente", description = "Este endpoint deleta Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado!"),
            @ApiResponse(responseCode = "404", description = CLIENTE_NAO_LOCALIZADO),
            @ApiResponse(responseCode = "500", description = SERVER_ERROR)
    })
    public ResponseEntity<Void> deletarClientePeloCnpj(
            @PathVariable("cnpj")
            @NotBlank(message = "cnpj é obrigatório!") final String cnpj) {

        clienteService.deletarClientePeloCnpj(cnpj);
        return ResponseEntity.noContent().build();
    }

}
