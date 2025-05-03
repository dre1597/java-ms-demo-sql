package org.example.javamsdemosql.configuration.rabbitmq;

import org.example.javamsdemosql.dto.SendMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static org.example.javamsdemosql.configuration.rabbitmq.RabbitMQConfig.DEMO_QUEUE;
import static org.example.javamsdemosql.configuration.rabbitmq.RabbitMQConfig.UNDELIVERED_QUEUE;

@Component
public class RetryingRabbitListener {
  private static final Logger log = LoggerFactory.getLogger(RetryingRabbitListener.class);
  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.retry.count}")
  private Integer retryCount;

  public RetryingRabbitListener(final RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = Objects.requireNonNull(rabbitTemplate);
  }

  @RabbitListener(queues = DEMO_QUEUE)
  public void primary(
      final SendMessageDto dto,
      @Header(required = false, name = "x-death") final Map<String, ?> xDeath
  ) {
    if (checkRetryCount(xDeath)) {
      log.warn("MAX RETRIES REACHED - Moving to undelivered queue for message: {}", dto);
      sendToUndelivered(dto);
    }
  }

  private boolean checkRetryCount(final Map<String, ?> xDeath) {
    if (xDeath != null && !xDeath.isEmpty()) {
      final var count = (Long) xDeath.get("count");
      return count >= retryCount;
    }
    return false;
  }

  private void sendToUndelivered(final SendMessageDto dto) {
    log.warn("maximum retry reached, send message to the undelivered queue, msg: {}", dto);
    this.rabbitTemplate.convertAndSend(UNDELIVERED_QUEUE, dto);
  }
}
