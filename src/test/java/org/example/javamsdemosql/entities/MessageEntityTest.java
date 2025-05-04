package org.example.javamsdemosql.entities;

import org.example.javamsdemosql.enums.MessageStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageEntityTest {
  @Test
  void shouldCreateMessageEntity() {
    final var id = UUID.randomUUID();
    final var now = LocalDateTime.now();

    final var jpaEntity = new MessageEntity(
        id,
        "any_title",
        "any_content",
        MessageStatus.PROCESSED,
        now,
        now,
        0
    );

    assertEquals(id, jpaEntity.getId());
    assertEquals("any_title", jpaEntity.getTitle());
    assertEquals("any_content", jpaEntity.getContent());
    assertEquals(MessageStatus.PROCESSED, jpaEntity.getStatus());
    assertEquals(now, jpaEntity.getCreatedAt());
    assertEquals(now, jpaEntity.getProcessedAt());
    assertEquals(0, jpaEntity.getRetryAttempts());
  }

  @Test
  void shouldSetAndGetFieldsCorrectly() {
    final var id = UUID.randomUUID();
    final var now = LocalDateTime.now();

    final var jpaEntity = new MessageEntity();

    jpaEntity.setId(id);
    jpaEntity.setTitle("any_title");
    jpaEntity.setContent("any_content");
    jpaEntity.setStatus(MessageStatus.PROCESSED);
    jpaEntity.setCreatedAt(now);
    jpaEntity.setProcessedAt(now);
    jpaEntity.setRetryAttempts(0);

    assertEquals(id, jpaEntity.getId());
    assertEquals("any_title", jpaEntity.getTitle());
    assertEquals("any_content", jpaEntity.getContent());
    assertEquals(MessageStatus.PROCESSED, jpaEntity.getStatus());
    assertEquals(now, jpaEntity.getCreatedAt());
    assertEquals(now, jpaEntity.getProcessedAt());
    assertEquals(0, jpaEntity.getRetryAttempts());
  }
}
