package com.example.demo.rabbit;


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


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue employeeQue_s() {
        return new Queue(EMPLOYEE_SAVE + TO);
    }

    @Bean
    public Queue positionQue_s() {
        return new Queue(POSITION_SAVE + TO);
    }

    @Bean
    public Queue attendanceQue_s() {
        return new Queue(ATTENDANCE_SAVE + TO);
    }

    @Bean
    public Queue departmentQue_s() {
        return new Queue(DEPARTMENT_SAVE + TO);
    }


    @Bean
    public Queue employeeQue_u() {
        return new Queue(EMPLOYEE_UPDATE + TO);
    }

    @Bean
    public Queue positionQue_u() {
        return new Queue(POSITION_UPDATE + TO);
    }

    @Bean
    public Queue attendanceQue_u() {
        return new Queue(ATTENDANCE_UPDATE + TO);
    }

    @Bean
    public Queue departmentQue_u() {
        return new Queue(DEPARTMENT_UPDATE + TO);
    }


    @Bean
    public Binding binding_es(@Qualifier("employeeQue_s") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(EMPLOYEE_SAVE);
    }


    @Bean
    public Binding binding_ps(@Qualifier("positionQue_s") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(POSITION_SAVE);
    }


    @Bean
    public Binding binding_as(@Qualifier("attendanceQue_s") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(ATTENDANCE_SAVE);
    }


    @Bean
    public Binding binding_ds(@Qualifier("departmentQue_s") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(DEPARTMENT_SAVE);
    }


    @Bean
    public Binding binding_eu(@Qualifier("employeeQue_u") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(EMPLOYEE_UPDATE);
    }


    @Bean
    public Binding binding_pu(@Qualifier("positionQue_u") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(POSITION_UPDATE);
    }


    @Bean
    public Binding binding_au(@Qualifier("attendanceQue_u") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(ATTENDANCE_UPDATE);
    }


    @Bean
    public Binding binding_du(@Qualifier("departmentQue_u") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(DEPARTMENT_UPDATE);
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
