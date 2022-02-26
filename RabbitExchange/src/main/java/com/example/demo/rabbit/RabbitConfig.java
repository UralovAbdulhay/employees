package com.example.demo.rabbit;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.demo.rabbit.Urls.*;


@Configuration
public class RabbitConfig {

// it will listen and send local exchanges

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }


    @Bean
    public Binding binding_as(@Qualifier("attendanceQue_s") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(ATTENDANCE_SAVE);
    }


    //************************************************* * ** * * ** * * * * * * * *
    @Bean
    public DirectExchange errorExchange() {
        return new DirectExchange("error_exchange_local");
    }


    @Bean
    public Queue errorQueue() {
        return QueueBuilder.durable("error_queue_local").build();
    }


    @Bean
    public Binding bindErrorQueue() {
        return BindingBuilder.bind(errorQueue()).to(errorExchange()).with("local_xatolar");
    }


    @Bean
    public Queue attendanceQue_s() {
        return QueueBuilder.durable(ATTENDANCE_SAVE + TO)
                .withArgument("x-dead-letter-exchange", "error_exchange_local")
                .withArgument("x-dead-letter-routing-key", "local_xatolar")
                .build()
                ;
    }


    // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *


    @Bean
    public MessageConverter messageConverter() {

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());

        return new Jackson2JsonMessageConverter(mapper);

    }


    @Bean
    public AmqpTemplate template(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }

}
