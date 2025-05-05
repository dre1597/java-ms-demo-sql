package org.example.javamsdemosql.controllers;

import org.example.javamsdemosql.dto.MessageDto;
import org.example.javamsdemosql.mocks.MessageMocks;
import org.example.javamsdemosql.services.FindAllMessagesUseCase;
import org.example.javamsdemosql.services.SendMessageUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {
  @Mock
  private SendMessageUseCase sendMessageUseCase;

  @Mock
  private FindAllMessagesUseCase findAllMessagesUseCase;

  @InjectMocks
  private MessageController controller;

  @Test
  void shouldSendMessage() {
    final var dto = new MessageDto("any_title", "any_content");
    controller.sendMessage(dto);
    verify(sendMessageUseCase).execute(dto);
  }

  @Test
  void shouldFindAllMessages() {
    final var createdMessage = MessageMocks.createTestMessage();

    when(findAllMessagesUseCase.execute()).thenReturn(List.of(createdMessage));

    final var result = controller.findAllMessages();

    assertEquals(1, result.size());
    assertEquals(createdMessage, result.getFirst());
  }
}
