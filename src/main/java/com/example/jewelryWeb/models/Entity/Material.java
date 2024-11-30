package com.example.jewelryWeb.models.Entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "material")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;

    @Column(nullable = false)
    private String materialName;
}
