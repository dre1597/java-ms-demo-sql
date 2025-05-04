package org.example.javamsdemosql.dto;

import org.example.javamsdemosql.enums.MessageStatus;

import java.time.LocalDateTime;

public record SaveMessageDto(
  String title,
  String content,
  MessageStatus status,
  int retryAttempts,
  LocalDateTime processedAt
) {
}
