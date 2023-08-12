package com.dynns.cloudtecnologia.logistica.rest.resource;

import com.dynns.cloudtecnologia.logistica.model.entity.Cliente;
import com.dynns.cloudtecnologia.logistica.model.mapper.ClienteMapper;
import com.dynns.cloudtecnologia.logistica.rest.dto.ClienteDTOCreate;
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

    @PostMapping()
    public ResponseEntity<ClienteDTOCreate> salvar(@RequestBody @Valid ClienteDTOCreate clienteDTOCreate) {
        Cliente salvo = clienteService.salvar(clienteDTOCreate);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(clienteDTOCreate);
    }


}
