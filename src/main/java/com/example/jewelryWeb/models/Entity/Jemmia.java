package com.example.jewelryWeb.models.Entity;
import lombok.*;

import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "jemmia")
public class Jemmia {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jemmia_id")
    private Long jemmia_id;

    private long thumbnail;
    
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String contentHeader;

    private long image;

    @Column(columnDefinition = "TEXT")
    private String contentFooter;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime publishedAt;

    private Boolean isActive;
}