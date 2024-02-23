package net.local.poc.orders.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public Queue queuePaymentProcess(){
        return new Queue("finance.payment.process");
    }

    @Bean
    public Queue queuePaymentConfirm(){
        return new Queue("order.payment.confirm");
    }

    @Bean
    public Queue queuePaymentReject(){
        return new Queue("order.payment.reject");
    }
    
    @Bean
    public Queue queueStockReserve(){
        return new Queue("warehouse.stock.reserve");
    }

    @Bean
    public Queue queueStockConfirm(){
        return new Queue("order.stock.confirm");
    }

    @Bean
    public Queue queueStockReject(){
        return new Queue("order.stock.reject");
    }

    @Bean
    public Queue queueStockRelease(){
        return new Queue("warehouse.stock.release");
    }

    @Bean
    public Queue queueShippmentDispatch(){
        return new Queue("warehouse.shipment.dispatch");
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
