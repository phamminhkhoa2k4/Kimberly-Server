package com.example.jewelryWeb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jewelryWeb.models.Entity.Shape;

public interface ShapeRepository extends JpaRepository<Shape, Long>  {
    Optional<Shape> findByShapeName(String shapeName);
}
