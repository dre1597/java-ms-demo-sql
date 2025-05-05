package org.example.javamsdemosql.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MessageEntityTest {
  @Test
  void shouldCreateMessageEntity() {
    final var id = UUID.randomUUID();

    final var jpaEntity = new MessageEntity(
        id,
        "any_title",
        "any_content"
    );

    assertEquals(id, jpaEntity.getId());
    assertEquals("any_title", jpaEntity.getTitle());
    assertEquals("any_content", jpaEntity.getContent());
  }

  @Test
  void shouldSetAndGetFieldsCorrectly() {
    final var id = UUID.randomUUID();
    final var jpaEntity = new MessageEntity();

    jpaEntity.setId(id);
    jpaEntity.setTitle("any_title");
    jpaEntity.setContent("any_content");
    jpaEntity.setCreatedAt(LocalDateTime.now());
    jpaEntity.setUpdatedAt(LocalDateTime.now());

    assertEquals(id, jpaEntity.getId());
    assertEquals("any_title", jpaEntity.getTitle());
    assertEquals("any_content", jpaEntity.getContent());
    assertNotNull(jpaEntity.getCreatedAt());
    assertNotNull(jpaEntity.getUpdatedAt());
  }
}
