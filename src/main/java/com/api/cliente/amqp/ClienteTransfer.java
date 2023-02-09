package com.api.cliente.amqp;

import com.api.cliente.dtos.ClienteDto;
import java.io.Serializable;

public class ClienteTransfer implements Serializable {
    ClienteDto clienteDto;
    String action;
    String error;

    public ClienteTransfer() {
    }

    public ClienteTransfer(ClienteDto clienteDto, String action) {
        this.clienteDto = clienteDto;
        this.action = action;
    }

    public ClienteDto getCliente() {
        return clienteDto;
    }

    public void setCliente(ClienteDto clienteDto) {
        this.clienteDto = clienteDto;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}