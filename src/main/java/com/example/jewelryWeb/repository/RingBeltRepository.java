package com.example.jewelryWeb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jewelryWeb.models.Entity.RingBelt;

public interface RingBeltRepository extends JpaRepository<RingBelt, Long> {
    Optional<RingBelt> findByBeltType(String beltType);
}
