package com.example.jewelryWeb.models.Entity;

import lombok.*;

import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Column(nullable = false , columnDefinition = "NVARCHAR(MAX)")
    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String contentHeader;

    private long image;

    private long thumbnail;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String contentFooter;

    private LocalDateTime publishedAt;

    private Boolean isActive;
}