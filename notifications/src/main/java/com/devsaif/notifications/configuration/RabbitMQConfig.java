package com.devsaif.notifications.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PAYMENT_EVENTS_EXCHANGE = "payment.events";
        public static final String NOTIFICATION_QUEUE = "notification.payment-successful.queue";

    @Bean
    public FanoutExchange paymentEventsExchange() {
        return new FanoutExchange(PAYMENT_EVENTS_EXCHANGE);
    }

    @Bean
    public Queue notificationPaymentSuccessfulQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Binding notificationQueueBinding(
            Queue bookingPaymentSuccessfulQueue,
            FanoutExchange paymentEventsExchange
    ) {
        return BindingBuilder
                .bind(bookingPaymentSuccessfulQueue)
                .to(paymentEventsExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();
        factory
                .setConnectionFactory(connectionFactory);
        factory
                .setMessageConverter(jsonMessageConverter);
        return factory;
    }
}