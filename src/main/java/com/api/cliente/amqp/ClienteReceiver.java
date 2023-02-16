package com.api.cliente.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Objects;

@RabbitListener(queues = "cliente")
public class ClienteReceiver {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private ClienteProducer clienteProducer;
    @Autowired
    private ClienteHelper clienteHelper;

    @RabbitHandler
    public ClienteTransfer receive(@Payload ClienteTransfer clienteTransfer) {
        if (clienteTransfer.getAction().equals("save-cliente")) {
            if (Objects.isNull(clienteTransfer.getClienteDto())) {
                clienteTransfer.setAction("failed-cliente");
                clienteTransfer.setMessage(("Nenhum dado de Cliente foi passado."));
                return clienteTransfer;
            }

            ResponseEntity<Object> response = clienteHelper.saveCliente(clienteTransfer.getClienteDto());

            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                clienteTransfer.setAction("success-cliente");
                return clienteTransfer;
            }

            clienteTransfer.setAction("failed-cliente");
            clienteTransfer.setMessage(Objects.requireNonNull(response.getBody()).toString());
            return clienteTransfer;
        }

        clienteTransfer.setAction("failed-cliente");
        clienteTransfer.setMessage("Ação informada não existe.");
        return clienteTransfer;
    }
}
