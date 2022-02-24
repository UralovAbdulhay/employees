package com.example.demo;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {


    @Bean
    public Queue employeeQue() {
        return new Queue(Urls.EMPLOYEE_QUE);
    }

    @Bean
    public Queue positionQue() {
        return new Queue(Urls.POSITION_QUE);
    }

    @Bean
    public Queue attendanceQue() {
        return new Queue(Urls.ATTENDANCE_QUE);
    }

    @Bean
    public Queue departmentQue() {
        return new Queue(Urls.DEPARTMENT_QUE);
    }


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Urls.TOPIC_EXCHANGE);
    }

    @Bean
    public Binding binding_e(@Qualifier("employeeQue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.EMPLOYEE_QUE);
    }


    @Bean
    public Binding binding_p(@Qualifier("positionQue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.POSITION_QUE);
    }


    @Bean
    public Binding binding_a(@Qualifier("attendanceQue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.ATTENDANCE_QUE);
    }


    @Bean
    public Binding binding_d(@Qualifier("departmentQue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.DEPARTMENT_QUE);
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate template(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }


}
