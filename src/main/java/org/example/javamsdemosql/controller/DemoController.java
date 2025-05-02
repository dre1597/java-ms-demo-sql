package org.example.javamsdemosql.controller;

import org.example.javamsdemosql.services.MessageProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class DemoController {
  private final MessageProducer messageProducer;

  public DemoController(final MessageProducer messageProducer) {
    this.messageProducer = Objects.requireNonNull(messageProducer);
  }

  @PostMapping("/send")
  public void sendMessage(@RequestBody final SendMessageDto dto) {
    messageProducer.sendMessage(dto.message());
  }
}
