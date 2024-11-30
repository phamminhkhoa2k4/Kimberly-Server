package com.example.jewelryWeb.models.Entity;
import lombok.*;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "diamond")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diamond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diamondId;

    @Column(nullable = false)
    private Double weight;

    private Double size;

    private String colorGrade;

    private String clarity;

    private String cutting;

    private String shape;

    @Column(nullable = false)
    private BigDecimal price;
}