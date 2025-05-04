package org.example.javamsdemosql.services;

import org.example.javamsdemosql.dto.SendMessageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;
import java.util.Optional;

import static org.example.javamsdemosql.configurations.amqp.RabbitMQConfig.UNDELIVERED_QUEUE;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetryingRabbitListenerTest {
  @Mock
  private RabbitTemplate rabbitTemplate;

  @Mock
  private SaveMessageUseCase saveMessageUseCase;

  @InjectMocks
  private RetryingRabbitListener listener;

  private final SendMessageDto testDto = new SendMessageDto("any_test", "any_content");

  @Test
  void shouldProcessFirstAttemptSuccessfully() {
    listener.primary(testDto, null);

    verify(saveMessageUseCase, times(2)).execute(any());
    verify(rabbitTemplate, never()).convertAndSend(Optional.ofNullable(any()), any());
  }

  @Test
  void shouldHandleFailedAttemptUnderRetryLimit() {
    final var xDeath = Map.of("count", 1L);

    try {
      listener.primary(testDto, xDeath);
      fail("Expected exception");
    } catch (Exception e) {
      verify(saveMessageUseCase, times(2)).execute(any());
      verify(rabbitTemplate, never()).convertAndSend(UNDELIVERED_QUEUE, testDto);
    }
  }

  @Test
  void shouldMoveToUndeliveredAfterMaxRetries() {
    final var xDeath = Map.of("count", 3L);
    ReflectionTestUtils.setField(listener, "retryCount", 3);

    listener.primary(testDto, xDeath);

    verify(saveMessageUseCase, times(2)).execute(any());
    verify(rabbitTemplate).convertAndSend(UNDELIVERED_QUEUE, testDto);
  }

  @Test
  void shouldHandleProcessingException() {
    doThrow(new RuntimeException()).when(saveMessageUseCase).execute(any());

    try {
      listener.primary(testDto, null);
      fail("Expected exception");
    } catch (Exception e) {
      verify(saveMessageUseCase, atLeastOnce()).execute(any());
    }
  }
}
