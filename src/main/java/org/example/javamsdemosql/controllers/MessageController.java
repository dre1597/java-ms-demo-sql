package org.example.javamsdemosql.controllers;

import org.example.javamsdemosql.dto.MessageDto;
import org.example.javamsdemosql.entities.MessageEntity;
import org.example.javamsdemosql.services.FindAllMessagesUseCase;
import org.example.javamsdemosql.services.SendMessageUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class MessageController {
  private final SendMessageUseCase sendMessageUseCase;
  private final FindAllMessagesUseCase findAllMessagesUseCase;

  public MessageController(
      final SendMessageUseCase sendMessageUseCase,
      final FindAllMessagesUseCase findAllMessagesUseCase
  ) {
    this.sendMessageUseCase = Objects.requireNonNull(sendMessageUseCase);
    this.findAllMessagesUseCase = Objects.requireNonNull(findAllMessagesUseCase);
  }

  @PostMapping("/send")
  public void sendMessage(@RequestBody final MessageDto dto) {
    this.sendMessageUseCase.execute(dto);
  }

  @GetMapping()
  public List<MessageEntity> findAllMessages() {
    return this.findAllMessagesUseCase.execute();
  }
}
