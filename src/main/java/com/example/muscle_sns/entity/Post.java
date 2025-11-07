package com.example.muscle_sns.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private LocalDateTime createdAt = LocalDateTime.now();
}
