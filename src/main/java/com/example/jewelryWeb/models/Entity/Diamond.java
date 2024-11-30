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
    private BigDecimal weight;

    private String size;

    private String colorGrade;

    private String Shape;
    
    private String clarity;

    private String cutting;

    @Column(nullable = false)
    private BigDecimal price;
}