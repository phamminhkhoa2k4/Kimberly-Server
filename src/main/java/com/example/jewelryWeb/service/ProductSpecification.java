package com.example.jewelryWeb.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.jewelryWeb.models.Entity.Category;
import org.springframework.data.jpa.domain.Specification;

import com.example.jewelryWeb.models.Entity.Gender;
import com.example.jewelryWeb.models.Entity.Product;

public class ProductSpecification {

    public static Specification<Product> hasMaterial(List<String> materials) {
        return (root, query, cb) -> materials == null || materials.isEmpty()
                ? null : root.get("material").in(materials);
    }


    public static Specification<Product> hasMetallicColor(List<String> metallicColors) {
        return (root, query, cb) -> metallicColors == null || metallicColors.isEmpty()
                ? null : root.get("metallicColor").in(metallicColors);
    }

    public static Specification<Product> hasGender(String gender) {
        return (root, query, cb) -> gender == null
                ? null : cb.equal(root.join("category").get("gender"), gender);
    }

    public static Specification<Product> hasCategory(String categoryName) {
        return (root, query, cb) -> categoryName == null
                ? null : cb.equal(root.join("category").get("categoryName"), categoryName);
    }

    public static Specification<Product> isWithinPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice != null && maxPrice != null) return cb.between(root.get("price"), minPrice, maxPrice);
            if (minPrice != null) return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
}
