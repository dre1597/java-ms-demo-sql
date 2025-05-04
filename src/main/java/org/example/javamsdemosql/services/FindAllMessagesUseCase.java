package org.example.javamsdemosql.services;

import org.example.javamsdemosql.entities.MessageEntity;
import org.example.javamsdemosql.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FindAllMessagesUseCase {
  private final MessageRepository messageRepository;

  public FindAllMessagesUseCase(final MessageRepository messageRepository) {
    this.messageRepository = Objects.requireNonNull(messageRepository);
  }

  public List<MessageEntity> execute() {
    return messageRepository.findAll();
  }
}
