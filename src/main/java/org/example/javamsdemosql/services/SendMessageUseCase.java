package org.example.javamsdemosql.services;

import org.example.javamsdemosql.dto.SendMessageDto;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SendMessageUseCase {
  private final MessageProducer messageProducer;

  public SendMessageUseCase(final MessageProducer messageProducer) {
    this.messageProducer = Objects.requireNonNull(messageProducer);
  }

  public void execute(final SendMessageDto dto) {
    messageProducer.sendMessage(dto);
  }
}
