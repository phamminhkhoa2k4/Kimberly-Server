package com.example.jewelryWeb.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jewelryWeb.models.Entity.News;
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    boolean existsByTitle(String title);
}