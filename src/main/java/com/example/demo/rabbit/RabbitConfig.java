package com.example.demo.rabbit;


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
    public TopicExchange exchange() {
        return new TopicExchange(Urls.TOPIC_EXCHANGE);
    }

    @Bean
    public Queue employeeQue_s() {
        return new Queue(Urls.EMPLOYEE_QUE);
    }

    @Bean
    public Queue positionQue_s() {
        return new Queue(Urls.POSITION_QUE);
    }

    @Bean
    public Queue attendanceQue_s() {
        return new Queue(Urls.ATTENDANCE_QUE);
    }

    @Bean
    public Queue departmentQue_s() {
        return new Queue(Urls.DEPARTMENT_QUE);
    }



    @Bean
    public Queue employeeQue_u() {
        return new Queue(Urls.EMPLOYEE_QUE);
    }

    @Bean
    public Queue positionQue_u() {
        return new Queue(Urls.POSITION_QUE);
    }

    @Bean
    public Queue attendanceQue_u() {
        return new Queue(Urls.ATTENDANCE_QUE);
    }

    @Bean
    public Queue departmentQue_u() {
        return new Queue(Urls.DEPARTMENT_QUE);
    }




    @Bean
    public Binding binding_es(@Qualifier("employeeQue_s") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.EMPLOYEE_SAVE);
    }


    @Bean
    public Binding binding_ps(@Qualifier("positionQue_s") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.POSITION_SAVE);
    }


    @Bean
    public Binding binding_as(@Qualifier("attendanceQue_s") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.ATTENDANCE_SAVE);
    }


    @Bean
    public Binding binding_ds(@Qualifier("departmentQue_s") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.DEPARTMENT_SAVE);
    }


    @Bean
    public Binding binding_eu(@Qualifier("employeeQue_u") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.EMPLOYEE_UPDATE);
    }


    @Bean
    public Binding binding_pu(@Qualifier("positionQue_u") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.POSITION_UPDATE);
    }


    @Bean
    public Binding binding_au(@Qualifier("attendanceQue_u") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.ATTENDANCE_UPDATE);
    }


    @Bean
    public Binding binding_du(@Qualifier("departmentQue_u") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Urls.DEPARTMENT_UPDATE);
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
