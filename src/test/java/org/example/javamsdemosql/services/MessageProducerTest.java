package org.example.javamsdemosql.services;

import org.example.javamsdemosql.configurations.amqp.RabbitMQConfig;
import org.example.javamsdemosql.dto.SendMessageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageProducerTest {
  @Mock
  private RabbitTemplate rabbitTemplate;

  @InjectMocks
  private MessageProducer messageProducer;

  @Test
  void shouldSendMessageToQueue() {
    final var dto = new SendMessageDto("any_title", "any_content");
    messageProducer.sendMessage(dto);
    verify(rabbitTemplate).convertAndSend(RabbitMQConfig.DEMO_QUEUE, dto);
  }
}
