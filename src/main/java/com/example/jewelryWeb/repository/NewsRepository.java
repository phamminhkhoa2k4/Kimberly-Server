package com.example.jewelryWeb.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jewelryWeb.models.Entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    boolean existsByTitle(String title);
    List<News> findByTitleContainingIgnoreCase(String title);
}