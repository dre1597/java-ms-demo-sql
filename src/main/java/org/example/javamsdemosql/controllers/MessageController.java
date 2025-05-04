package org.example.javamsdemosql.controllers;

import org.example.javamsdemosql.dto.MessageDto;
import org.example.javamsdemosql.entities.MessageEntity;
import org.example.javamsdemosql.enums.MessageStatus;
import org.example.javamsdemosql.services.FindAllMessagesUseCase;
import org.example.javamsdemosql.services.FindByStatusUseCase;
import org.example.javamsdemosql.services.SendMessageUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class MessageController {
  private final SendMessageUseCase sendMessageUseCase;
  private final FindAllMessagesUseCase findAllMessagesUseCase;
  private final FindByStatusUseCase findByStatusUseCase;

  public MessageController(
      final SendMessageUseCase sendMessageUseCase,
      final FindAllMessagesUseCase findAllMessagesUseCase,
      final FindByStatusUseCase findByStatusUseCase
  ) {
    this.sendMessageUseCase = Objects.requireNonNull(sendMessageUseCase);
    this.findAllMessagesUseCase = Objects.requireNonNull(findAllMessagesUseCase);
    this.findByStatusUseCase = Objects.requireNonNull(findByStatusUseCase);
  }

  @PostMapping("/send")
  public void sendMessage(@RequestBody final MessageDto dto) {
    this.sendMessageUseCase.execute(dto);
  }

  @GetMapping()
  public List<MessageEntity> findAllMessages() {
    return this.findAllMessagesUseCase.execute();
  }

  @GetMapping("/status/{status}")
  public List<MessageEntity> findByStatus(@PathVariable final String status) {
    return this.findByStatusUseCase.execute(MessageStatus.valueOf(status));
  }
}
