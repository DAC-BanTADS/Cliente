package com.api.cliente.controllers;

import com.api.cliente.models.ClienteModel;
import com.api.cliente.services.ClienteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/cliente")
public class ClienteController {
    final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteModel>> getAllClientes(){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getClienteById(@PathVariable(value = "id") UUID id){
        Optional<ClienteModel> clienteModelOptional = clienteService.findById(id);
        if (!clienteModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(clienteModelOptional.get());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getClienteByEmail(@PathVariable(value = "email") String email){
        Optional<ClienteModel> clienteModelOptional = clienteService.findByEmail(email);
        if (!clienteModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(clienteModelOptional.get());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Object> getClienteByCpf(@PathVariable(value = "cpf") String cpf){
        Optional<ClienteModel> clienteModelOptional = clienteService.findByCpf(cpf);
        if (!clienteModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(clienteModelOptional.get());
    }
}
