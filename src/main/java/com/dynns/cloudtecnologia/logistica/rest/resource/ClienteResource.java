package com.dynns.cloudtecnologia.logistica.rest.resource;

import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.model.entity.Endereco;
import com.dynns.cloudtecnologia.logistica.model.mapper.ClienteMapper;
import com.dynns.cloudtecnologia.logistica.model.mapper.EnderecoMapper;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOCreate;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOResource;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOResourceList;
import com.dynns.cloudtecnologia.logistica.rest.dto.EnderecoDTOResource;
import com.dynns.cloudtecnologia.logistica.service.impl.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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


    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTOResource> buscarPeloId(@PathVariable("id") Long id) {
        Cliente cliente = clienteService.buscarPeloId(id);
        return ResponseEntity.ok().body(clienteMapper.clienteToClienteDTOResource(cliente));
    }

    @GetMapping("/{id}/endereco")
    public ResponseEntity<EnderecoDTOResource> buscarEnderecoCliente(@PathVariable("id") Long id) {
        Cliente cliente = clienteService.buscarPeloId(id);
        return ResponseEntity.ok().body(enderecoMapper.clienteToEnderecoDTOResource(cliente));
    }


}
