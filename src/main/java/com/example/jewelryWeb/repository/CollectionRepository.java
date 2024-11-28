package com.example.jewelryWeb.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jewelryWeb.models.Entity.Collection;
@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
}