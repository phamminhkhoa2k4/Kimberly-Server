package com.example.jewelryWeb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jewelryWeb.models.Entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findByMaterialName(String materialName);
    List<Material> findByMaterialNameIn(List<String> materialNames);
}
