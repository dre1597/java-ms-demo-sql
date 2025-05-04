package org.example.javamsdemosql.services;

import org.example.javamsdemosql.dto.MessageDto;
import org.example.javamsdemosql.dto.SaveMessageDto;
import org.example.javamsdemosql.enums.MessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static org.example.javamsdemosql.configurations.amqp.RabbitMQConfig.DEMO_QUEUE;
import static org.example.javamsdemosql.configurations.amqp.RabbitMQConfig.UNDELIVERED_QUEUE;

@Component
public class RetryingRabbitListener {
  private static final Logger log = LoggerFactory.getLogger(RetryingRabbitListener.class);
  private final RabbitTemplate rabbitTemplate;
  private final SaveMessageUseCase saveMessageUseCase;

  @Value("${rabbitmq.retry.count}")
  private Integer retryCount;

  public RetryingRabbitListener(
      final RabbitTemplate rabbitTemplate,
      final SaveMessageUseCase saveMessageUseCase
  ) {
    this.rabbitTemplate = Objects.requireNonNull(rabbitTemplate);
    this.saveMessageUseCase = Objects.requireNonNull(saveMessageUseCase);
  }

  @RabbitListener(queues = DEMO_QUEUE)
  public void primary(
      final MessageDto dto,
      @Header(required = false, name = "x-death") final Map<String, ?> xDeath
  ) {
    final var attempt = xDeath != null ? (Long) xDeath.get("count") + 1 : 1;
    log.info("Attempt {} - Processing message: {}", attempt, dto);
    this.saveMessage(dto, MessageStatus.RECEIVED, (int) attempt);

    try {
      if (checkRetryCount(xDeath)) {
        log.warn("MAX RETRIES REACHED - Moving to undelivered queue for message: {}", dto);
        this.sendToUndelivered(dto);
        this.saveMessage(dto, MessageStatus.UNDELIVERED, (int) attempt);
        return;
      }
      log.info("Message processed: {} on attempt {}", dto, attempt);
      this.saveMessage(dto, MessageStatus.PROCESSED, (int) attempt);
    } catch (final Exception e) {
      log.error("Failed to process message: {}", dto, e);
      this.saveMessage(dto, MessageStatus.FAILED, (int) attempt);
      throw e;
    }
  }

  private void saveMessage(
      final MessageDto dto,
      final MessageStatus status,
      final int retryAttempts
  ) {
    if (status == MessageStatus.PROCESSED) {
      final var saveMessageDto = new SaveMessageDto(
          dto.title(),
          dto.message(),
          status,
          retryAttempts,
          LocalDateTime.now()
      );

      this.saveMessageUseCase.execute(saveMessageDto);
      return;
    }

    final var saveMessageDto = new SaveMessageDto(
        dto.title(),
        dto.message(),
        status,
        retryAttempts,
        null
    );

    this.saveMessageUseCase.execute(saveMessageDto);
  }

  private boolean checkRetryCount(final Map<String, ?> xDeath) {
    if (xDeath != null && !xDeath.isEmpty()) {
      final var count = (Long) xDeath.get("count");
      return count >= retryCount;
    }
    return false;
  }

  private void sendToUndelivered(final MessageDto dto) {
    log.warn("maximum retry reached, send message to the undelivered queue, msg: {}", dto);
    this.rabbitTemplate.convertAndSend(UNDELIVERED_QUEUE, dto);
  }
}
