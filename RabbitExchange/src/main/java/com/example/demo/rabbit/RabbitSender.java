package com.example.demo.rabbit;

import com.example.demo.base.BaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitSender {

    private final RabbitTemplate template;


    public <T extends BaseRequest> void sendObject(T obj, String exchange, String routingKey) {
        System.out.println(String.format("%s  == %s  ===  %s", obj, exchange, routingKey));
        template.convertAndSend(exchange, routingKey, obj);
    }


}
