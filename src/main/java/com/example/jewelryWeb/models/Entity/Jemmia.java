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
    
    @Column(nullable = false , columnDefinition = "NVARCHAR(MAX)")
    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String contentHeader;

    private long image;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String contentFooter;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime publishedAt;

    private Boolean isActive;
}