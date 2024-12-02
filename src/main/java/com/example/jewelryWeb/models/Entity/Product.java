package com.example.jewelryWeb.models.Entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String productName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)

    private Category category;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToMany
    @JoinTable(name = "product_metallic_color", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "metallic_color_id"))
    private Set<MetallicColor> metallicColors;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ring_belt_id")
    private RingBelt ringBelt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "material_id", nullable = true)
    private Material material;

    private Float discount;

    @Column(columnDefinition = "TEXT")
    private String images;

    private Boolean isFeatured;

    private Boolean isActive;

    private Boolean isIncludeMasterDiamond;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shape_id", nullable = true)
    private Shape shape;

    private boolean male;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
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
