package com.example.jewelryWeb.models.Entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false,columnDefinition = "NVARCHAR(MAX)")
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @Column(nullable = false)
    private BigDecimal price;
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String metallicColor;

    private String ringBelt;
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String material;

    private Float discount;

    @Column(columnDefinition = "TEXT")
    private String images;

    private Boolean isFeatured;

    private Boolean isActive;

    private Boolean isIncludeMasterDiamond;

    private String Shape;


    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "products")
    private Set<Collection> collections;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}