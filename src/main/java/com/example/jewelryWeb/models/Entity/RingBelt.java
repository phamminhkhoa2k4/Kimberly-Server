package com.example.jewelryWeb.models.Entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "ring_belt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RingBelt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ringBeltId;

    @Column(nullable = false)
    private String beltType;
}
