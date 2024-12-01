package com.example.jewelryWeb.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.jewelryWeb.models.Entity.Category;
import org.springframework.data.jpa.domain.Specification;

import com.example.jewelryWeb.models.Entity.Gender;
import com.example.jewelryWeb.models.Entity.Product;

public class ProductSpecification {

    // Specification for filtering by materials
    public static Specification<Product> hasMaterial(List<String> materials) {
        return (root, query, criteriaBuilder) -> {
            if (materials == null || materials.isEmpty()) {
                return null;
            }
            return root.join("material").get("materialName").in(materials);
        };
    }

    // Specification for filtering by category name
    public static Specification<Product> hasCategory(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            if (categoryName == null || categoryName.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("category").get("categoryName"), categoryName);
        };
    }

    // Specification for filtering by metallic colors
    public static Specification<Product> hasMetallicColor(List<String> metallicColors) {
        return (root, query, criteriaBuilder) -> {
            if (metallicColors == null || metallicColors.isEmpty()) {
                return null;
            }
            return root.join("metallicColors").get("colorName").in(metallicColors);
        };
    }

    // Specification for filtering by gender
    public static Specification<Product> hasGender(String gender) {
        return (root, query, criteriaBuilder) -> {
            if (gender == null || gender.isEmpty()) {
                return null;
            }
            boolean isMale = gender.equalsIgnoreCase("male");
            return criteriaBuilder.equal(root.get("male"), isMale);
        };
    }

    // Specification for filtering by price range
    public static Specification<Product> isWithinPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        };
    }
}