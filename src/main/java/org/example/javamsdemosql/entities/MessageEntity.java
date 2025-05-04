package org.example.javamsdemosql.entities;

import jakarta.persistence.*;
import org.example.javamsdemosql.enums.MessageStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class MessageEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, length = 100)
  private String title;

  @Column(nullable = false, length = 1000)
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MessageStatus status;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Column
  private LocalDateTime processedAt;

  @Column(nullable = false)
  private int retryAttempts;

  public MessageEntity(
      final UUID id,
      final String title,
      final String content,
      final MessageStatus status,
      final LocalDateTime createdAt,
      final LocalDateTime processedAt,
      final int retryAttempts
  ) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.status = status;
    this.createdAt = createdAt;
    this.processedAt = processedAt;
    this.retryAttempts = retryAttempts;
  }

  public MessageEntity() {}

  public UUID getId() {
    return this.id;
  }

  public void setId(final UUID id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(final String content) {
    this.content = content;
  }

  public MessageStatus getStatus() {
    return this.status;
  }

  public void setStatus(final MessageStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(final LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getProcessedAt() {
    return this.processedAt;
  }

  public void setProcessedAt(final LocalDateTime processedAt) {
    this.processedAt = processedAt;
  }

  public int getRetryAttempts() {
    return this.retryAttempts;
  }

  public void setRetryAttempts(final int retryAttempts) {
    this.retryAttempts = retryAttempts;
  }
}
