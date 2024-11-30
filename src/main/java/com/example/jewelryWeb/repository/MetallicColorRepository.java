package com.example.jewelryWeb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jewelryWeb.models.Entity.MetallicColor;

public interface MetallicColorRepository extends JpaRepository<MetallicColor, Long> {
    Optional<MetallicColor> findByColorName(String colorName);
}
