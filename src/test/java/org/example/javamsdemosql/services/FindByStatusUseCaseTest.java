package org.example.javamsdemosql.services;

import org.example.javamsdemosql.enums.MessageStatus;
import org.example.javamsdemosql.mocks.MessageMocks;
import org.example.javamsdemosql.repositories.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByStatusUseCaseTest {
  @Mock
  private MessageRepository repository;

  @InjectMocks
  private FindByStatusUseCase useCase;

  @Test
  void shouldReturnMessagesByStatus() {
    final var createdMessage = MessageMocks.createTestMessage();

    when(repository.findByStatus(MessageStatus.PROCESSED)).thenReturn(List.of(createdMessage));

    final var result = useCase.execute(MessageStatus.PROCESSED);

    assertEquals(1, result.size());
    assertEquals(createdMessage, result.getFirst());
  }

  @Test
  void shouldReturnEmptyListWhenNoMessagesWithStatus() {
    final var status = MessageStatus.FAILED;

    when(repository.findByStatus(status)).thenReturn(List.of());

    final var result = useCase.execute(status);

    assertEquals(0, result.size());
  }

  @Test
  void shouldHandleNullStatus() {
    when(repository.findByStatus(null)).thenReturn(List.of());

    final var result = useCase.execute(null);

    assertEquals(0, result.size());
  }
}
