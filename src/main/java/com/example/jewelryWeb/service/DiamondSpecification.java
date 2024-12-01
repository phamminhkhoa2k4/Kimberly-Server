package com.example.jewelryWeb.service;


import com.example.jewelryWeb.models.Entity.Diamond;
import com.example.jewelryWeb.models.Entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class DiamondSpecification {


    public static Specification<Diamond> hasShape(String shape) {
        return (root, query, cb) -> shape == null
                ? null : cb.equal(root.get("shape"), shape);
    }


    public static Specification<Diamond> hasClarity(String clarity){
        return (root, query, cb) -> clarity == null ? null : cb.equal(root.get("clarity"),clarity);
    }

    public static Specification<Diamond> hasColor (String color){
        return ( root,query,cb) -> color  == null ? null : cb.equal(root.get("colorGrade"), color);
    }


    public static Specification<Diamond> isWithinPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice != null && maxPrice != null) return cb.between(root.get("price"), minPrice, maxPrice);
            if (minPrice != null) return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }



    public static Specification<Diamond> isWithinCaratRange(Double minWeight, Double maxWeight) {
        return (root, query, cb) -> {
            if (minWeight == null && maxWeight == null) return null;
            if (minWeight != null && maxWeight != null) return cb.between(root.get("weight"), minWeight, maxWeight);
            if (minWeight != null) return cb.greaterThanOrEqualTo(root.get("weight"), minWeight);
            return cb.lessThanOrEqualTo(root.get("weight"), maxWeight);
        };
    }

    public static Specification<Diamond> isWithinSizeRange(Double minSize, Double maxSize) {
        return (root, query, cb) -> {
            if (minSize == null && maxSize == null) return null;
            if (minSize != null && maxSize != null) return cb.between(root.get("size"), minSize, maxSize);
            if (minSize != null) return cb.greaterThanOrEqualTo(root.get("size"), minSize);
            return cb.lessThanOrEqualTo(root.get("size"), maxSize);
        };
    }
}
