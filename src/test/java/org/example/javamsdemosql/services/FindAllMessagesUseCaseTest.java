package org.example.javamsdemosql.services;

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
class FindAllMessagesUseCaseTest {
  @Mock
  private MessageRepository repository;

  @InjectMocks
  private FindAllMessagesUseCase useCase;

  @Test
  void shouldReturnAllMessages() {
    final var createdMessage = MessageMocks.createTestMessage();

    when(repository.findAll()).thenReturn(List.of(createdMessage));

    final var result = useCase.execute();

    assertEquals(1, result.size());
    assertEquals(createdMessage, result.getFirst());
  }

  @Test
  void shouldReturnEmptyList() {
    when(repository.findAll()).thenReturn(List.of());

    final var result = useCase.execute();

    assertEquals(0, result.size());
  }
}
