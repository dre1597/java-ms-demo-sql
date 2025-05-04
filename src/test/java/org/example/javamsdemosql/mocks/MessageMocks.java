package org.example.javamsdemosql.mocks;

import org.example.javamsdemosql.entities.MessageEntity;
import org.example.javamsdemosql.enums.MessageStatus;

public class MessageMocks {
  public static MessageEntity createTestMessage() {
    final var messageJpa = new MessageEntity();
    messageJpa.setTitle("any_title");
    messageJpa.setContent("any_content");
    messageJpa.setStatus(MessageStatus.PROCESSED);
    messageJpa.setRetryAttempts(0);
    return messageJpa;
  }
}
