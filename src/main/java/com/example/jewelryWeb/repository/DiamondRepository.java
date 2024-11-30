package com.example.jewelryWeb.repository;
import java.math.BigDecimal;
import java.util.List;

import com.example.jewelryWeb.models.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.jewelryWeb.models.Entity.Diamond;
@Repository
public interface DiamondRepository extends JpaRepository<Diamond, Long> , JpaSpecificationExecutor<Diamond> {

    @Query("SELECT d FROM Diamond d WHERE " +
            "(:minWeight IS NULL OR d.weight >= :minWeight) AND " +
            "(:maxWeight IS NULL OR d.weight <= :maxWeight) AND " +
            "(:minPrice IS NULL OR d.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR d.price <= :maxPrice) AND " +
            "(:size IS NULL OR d.size = :size) AND " +
            "(:colorGrade IS NULL OR d.colorGrade = :colorGrade) AND " +
            "(:clarity IS NULL OR d.clarity = :clarity) AND " +
            "(:shape IS NULL OR d.shape = :shape)")
    List<Diamond> filterDiamonds(BigDecimal minWeight, BigDecimal maxWeight,
                                 BigDecimal minPrice, BigDecimal maxPrice,
                                 String size, String colorGrade, String clarity,
                                 String shape);
}