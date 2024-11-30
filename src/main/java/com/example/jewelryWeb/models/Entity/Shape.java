package com.example.jewelryWeb.models.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shape")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shapeId;

    @Column(nullable = false)
    private String shapeName;
}