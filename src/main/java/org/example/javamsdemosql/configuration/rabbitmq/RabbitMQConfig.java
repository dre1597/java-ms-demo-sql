package org.example.javamsdemosql.configuration.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  public static final String DEMO_QUEUE = "demo.queue";

  @Bean
  public Queue demoQueue() {
    return new Queue(DEMO_QUEUE, false);
  }
}
