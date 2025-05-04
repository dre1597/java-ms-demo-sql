package org.example.javamsdemosql.configurations.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitMQConfig {
  public static final String DEMO_QUEUE = "demo.queue";
  public static final String UNDELIVERED_QUEUE = "demo.undelivered";
  private static final String MAIN_ROUTING_KEY = "routing_key";
  private static final String EXCHANGE_NAME = "retry-exchange";
  private static final String RETRY_QUEUE = "demo.retry";
  @Value("${rabbitmq.retry.delay-in-ms}")
  private Integer retryDelay;

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final var rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    return rabbitTemplate;
  }

  @Bean
  MessageHandlerMethodFactory messageHandlerMethodFactory() {
    final var messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
    messageHandlerMethodFactory.setMessageConverter(new MappingJackson2MessageConverter());
    return messageHandlerMethodFactory;
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean
  public Queue demoQueue() {
    return QueueBuilder.durable(DEMO_QUEUE)
        .deadLetterExchange(EXCHANGE_NAME)
        .deadLetterRoutingKey(RETRY_QUEUE)
        .quorum()
        .build();
  }

  @Bean
  Queue retryQueue() {
    return QueueBuilder.durable(RETRY_QUEUE)
        .deadLetterExchange(EXCHANGE_NAME)
        .deadLetterRoutingKey(MAIN_ROUTING_KEY)
        .ttl(retryDelay)
        .quorum()
        .build();
  }

  @Bean
  Queue undeliveredQueue() {
    return QueueBuilder.durable(UNDELIVERED_QUEUE)
        .quorum()
        .build();
  }

  @Bean
  Binding mainBinding(final Queue demoQueue, final TopicExchange exchange) {
    return BindingBuilder.bind(demoQueue).to(exchange).with(MAIN_ROUTING_KEY);
  }

  @Bean
  Binding retryBinding(Queue retryQueue, TopicExchange exchange) {
    return BindingBuilder.bind(retryQueue).to(exchange).with(RETRY_QUEUE);
  }

  @Bean
  Binding undeliveredBinding(Queue undeliveredQueue, TopicExchange exchange) {
    return BindingBuilder.bind(undeliveredQueue).to(exchange).with(UNDELIVERED_QUEUE);
  }
}
