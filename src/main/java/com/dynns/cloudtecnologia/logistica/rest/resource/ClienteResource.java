package com.dynns.cloudtecnologia.logistica.rest.resource;

import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.model.mapper.ClienteMapper;
import com.dynns.cloudtecnologia.logistica.model.mapper.EnderecoMapper;
import com.dynns.cloudtecnologia.logistica.rest.dto.*;
import com.dynns.cloudtecnologia.logistica.service.impl.ClienteServiceImpl;
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
public class ClienteResource {

    @Autowired
    private ClienteServiceImpl clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @PostMapping
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
    public ResponseEntity<ClienteDTOResource> buscarPeloCnpj(@PathVariable("cnpj") @NotBlank(message = "cnpj é obrigatório!") final String cnpj) {
        Cliente cliente = clienteService.buscarPeloCnpj(cnpj);
        return ResponseEntity.ok().body(clienteMapper.clienteToClienteDTOResource(cliente));
    }

    @GetMapping("/{cnpj}/endereco")
    public ResponseEntity<EnderecoDTOResource> buscarEnderecoCliente(@PathVariable("cnpj") @NotBlank(message = "cnpj é obrigatório!") final String cnpj) {
        Cliente cliente = clienteService.buscarPeloCnpj(cnpj);
        return ResponseEntity.ok().body(enderecoMapper.clienteToEnderecoDTOResource(cliente));
    }

    @PutMapping("/{cnpj}")
    public ResponseEntity<ClienteDTOResourceList> atualizarCliente(
            @PathVariable("cnpj") @NotBlank(message = "cnpj é obrigatório!") final String cnpj,
            @Valid @RequestBody final ClienteDTOUpdate clienteDTOUpdate) {

        Cliente clienteAtualizado = clienteService.atualizarCliente(clienteDTOUpdate, cnpj);
        return ResponseEntity.ok().body(clienteMapper.clienteToClienteDTOResourceList(clienteAtualizado));
    }
  
    @GetMapping
    public ResponseEntity<Page<Cliente>> listarTodosPaginadoFilter(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody ClienteDTOResourceList clienteFilter
    ) {

        Page<Cliente> clientePageFilter = clienteService.listarTodosPageFilter(page, size, clienteFilter);

        return ResponseEntity.ok().body(clientePageFilter);
    }

    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> deletarClientePeloCnpj(
            @PathVariable("cnpj")
            @NotBlank(message = "cnpj é obrigatório!") final String cnpj) {

        clienteService.deletarClientePeloCnpj(cnpj);
        return ResponseEntity.noContent().build();
    }

}
