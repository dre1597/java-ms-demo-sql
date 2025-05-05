package org.example.javamsdemosql.services;

import org.example.javamsdemosql.dto.SaveMessageDto;
import org.example.javamsdemosql.entities.MessageEntity;
import org.example.javamsdemosql.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SaveMessageUseCase {
  private final MessageRepository messageRepository;

  public SaveMessageUseCase(final MessageRepository messageRepository) {
    this.messageRepository = Objects.requireNonNull(messageRepository);
  }

  public void execute(final SaveMessageDto dto) {
    final var messageJpa = new MessageEntity();
    messageJpa.setTitle(dto.title());
    messageJpa.setContent(dto.content());
    messageRepository.save(messageJpa);
  }
}
