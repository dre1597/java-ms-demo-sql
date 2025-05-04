package org.example.javamsdemosql.services;

import org.example.javamsdemosql.dto.SaveMessageDto;
import org.example.javamsdemosql.entities.MessageEntity;
import org.example.javamsdemosql.enums.MessageStatus;
import org.example.javamsdemosql.repositories.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveMessageUseCaseTest {
  @Mock
  private MessageRepository repository;

  @InjectMocks
  private SaveMessageUseCase useCase;

  @Test
  void shouldSaveMessageWithAllFields() {
    final var dto = new SaveMessageDto(
        "any_title",
        "any_content",
        MessageStatus.PROCESSED,
        2,
        null
    );

    useCase.execute(dto);

    final var captor = ArgumentCaptor.forClass(MessageEntity.class);
    verify(repository).save(captor.capture());

    final var savedMessage = captor.getValue();
    assertEquals(dto.title(), savedMessage.getTitle());
    assertEquals(dto.content(), savedMessage.getContent());
    assertEquals(dto.status(), savedMessage.getStatus());
    assertEquals(dto.retryAttempts(), savedMessage.getRetryAttempts());
  }

  @Test
  void shouldHandleNullProcessedAt() {
    final var dto = new SaveMessageDto(
        "any_title",
        "any_content",
        MessageStatus.RECEIVED,
        0,
        null
    );

    useCase.execute(dto);

    final var captor = ArgumentCaptor.forClass(MessageEntity.class);
    verify(repository).save(captor.capture());

    final var savedMessage = captor.getValue();
    assertEquals(dto.title(), savedMessage.getTitle());
    assertNull(savedMessage.getProcessedAt());
  }

  @Test
  void shouldSaveMessageWithProcessedAt() {
    final var processedAt = LocalDateTime.now();
    final var dto = new SaveMessageDto(
        "any_title",
        "any_content",
        MessageStatus.PROCESSED,
        0,
        processedAt
    );

    useCase.execute(dto);

    final var captor = ArgumentCaptor.forClass(MessageEntity.class);
    System.out.println(captor);
    verify(repository).save(captor.capture());

    final var savedMessage = captor.getValue();
    assertEquals(processedAt, savedMessage.getProcessedAt());
  }
}
