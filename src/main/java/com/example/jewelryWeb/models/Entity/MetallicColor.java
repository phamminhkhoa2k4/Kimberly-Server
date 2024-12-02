package com.example.jewelryWeb.models.Entity;


import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "metallic_color")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetallicColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metallicColorId;

    @Column(nullable = false)
    private String colorName;
}
