package org.example.javamsdemosql.services;

import org.example.javamsdemosql.configuration.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
  @RabbitListener(queues = RabbitMQConfig.DEMO_QUEUE)
  public void receiveMessage(final String message) {
    System.out.println("Received message: " + message);
  }
}
