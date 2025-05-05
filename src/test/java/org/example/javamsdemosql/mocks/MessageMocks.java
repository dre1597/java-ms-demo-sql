package org.example.javamsdemosql.mocks;

import org.example.javamsdemosql.entities.MessageEntity;

public class MessageMocks {
  public static MessageEntity createTestMessage() {
    final var messageJpa = new MessageEntity();
    messageJpa.setTitle("any_title");
    messageJpa.setContent("any_content");
    return messageJpa;
  }
}
