package org.example.javamsdemosql.services;

import org.example.javamsdemosql.configurations.amqp.RabbitMQConfig;
import org.example.javamsdemosql.dto.MessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MessageProducer {
  private final RabbitTemplate rabbitTemplate;

  public MessageProducer(final RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = Objects.requireNonNull(rabbitTemplate);
  }

  public void sendMessage(final MessageDto dto) {
    rabbitTemplate.convertAndSend(RabbitMQConfig.DEMO_QUEUE, dto);
  }
}
