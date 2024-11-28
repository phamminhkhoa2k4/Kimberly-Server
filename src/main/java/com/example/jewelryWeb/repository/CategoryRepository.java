package com.example.jewelryWeb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jewelryWeb.models.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);
    Optional<Category> findByCategoryNameAndParentCategory(String categoryName, Category parentCategory);
}