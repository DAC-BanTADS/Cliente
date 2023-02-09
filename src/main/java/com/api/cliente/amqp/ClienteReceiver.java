package com.api.cliente.amqp;

import com.api.cliente.controllers.ClienteController;
import com.api.cliente.dtos.ClienteDto;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;

@RabbitListener(queues = "cliente")
public class ClienteReceiver {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private ClienteProducer clienteProducer;
    @Autowired
    private ClienteController clienteController;

    @RabbitHandler
    public ClienteTransfer receive(@Payload ClienteTransfer clienteTransfer) {
        if (clienteTransfer.getAction().equals("save-cliente")) {
            ResponseEntity<Object> response = clienteController.saveCliente(clienteTransfer.getCliente());

            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                ClienteDto clienteDto = (ClienteDto) response.getBody();
                clienteTransfer.setCliente(clienteDto);
                clienteTransfer.setAction("success-cliente");

                return clienteTransfer;
            }

            clienteTransfer.setError("Erro interno ao criar o cliente");
            clienteTransfer.setAction("failed-cliente");

            return clienteTransfer;
        }

        return null;
    }
}
