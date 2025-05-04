package org.example.javamsdemosql.services;

import org.example.javamsdemosql.dto.MessageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SendMessageUseCaseTest {
  @Mock
  private MessageProducer messageProducer;

  @InjectMocks
  private SendMessageUseCase useCase;

  @Test
  void shouldSendMessage() {
    final var dto = new MessageDto("any_title", "any_content");
    useCase.execute(dto);
    verify(messageProducer).sendMessage(dto);
  }
}
