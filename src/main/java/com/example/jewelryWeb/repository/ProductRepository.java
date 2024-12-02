package com.example.jewelryWeb.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jewelryWeb.models.Entity.*;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
        @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p WHERE p.images LIKE CONCAT('%', :imageId, '%')")
        boolean existsByImageId(@Param("imageId") String imageId);

        List<Product> findByCategory_CategoryIdAndMale(Long categoryId, boolean male);

        List<Product> findByCategory_CategoryId(Long categoryId);

        boolean existsByProductName(String productName);

        // @Query("SELECT p FROM Product p WHERE p.category = :category AND
        // p.productId<> :productId")
        // List<Product> findRelatedProductsByCategory(@Param("category") Category
        // category, @Param("productId") Long productId);

        // @Query("SELECT p FROM Product p WHERE p.metallicColor = :metallicColor AND
        // p.productId <> :productId")
        // List<Product> findRelatedProductsByMetallicColor(@Param("metallicColor")
        // String metallicColor, @Param("productId") Long productId);

        // @Query("SELECT p FROM Product p WHERE p.material = :material AND
        // p.productId<> :productId")
        // List<Product> findRelatedProductsByMaterial(@Param("material") String
        // material, @Param("productId") Long productId);

        List<Product> findByProductNameContainingIgnoreCase(String name);

        @Query("SELECT p FROM Product p WHERE p.id IN :ids")
        List<Product> findAllById(@Param("ids") List<Integer> ids);

        // @Query("SELECT p FROM Product p WHERE "
        // + "(p.category.categoryId = :categoryId OR :categoryId IS NULL) "
        // + "AND (p.price >= :minPrice OR :minPrice IS NULL) "
        // + "AND (p.price <= :maxPrice OR :maxPrice IS NULL) "
        // + "AND (p.material = :material OR :material IS NULL) "
        // + "AND (p.colorName = :metallicColor OR :metallicColor IS NULL) "
        // + "AND (p.ringBelt = :gender OR :gender IS NULL)")
        // List<Product> filterProducts(
        // @Param("categoryId") Long categoryId,
        // @Param("minPrice") BigDecimal minPrice,
        // @Param("maxPrice") BigDecimal maxPrice,
        // @Param("material") String material,
        // @Param("metallicColor") String metallicColor,
        // @Param("gender") String gender);

        // boolean existsByProductName(String productName);

        @Query("SELECT p FROM Product p " +
                        "JOIN p.metallicColors mc " +
                        "WHERE (p.category = :category " +
                        "OR mc IN :metallicColors " +
                        "OR p.material = :material) " +
                        "AND p.productId <> :productId")
        List<Product> findRelatedProducts(
                        @Param("category") Category category,
                        @Param("metallicColors") Set<MetallicColor> metallicColors,
                        @Param("material") Material material,
                        @Param("productId") Long productId);

        // http://localhost:8080/api/product/filter?materials=Gold,Silver&metallicColors=Red,Blue&categoryName=Ring&sortBy=priceAsc&gender=male&minPrice=1000&maxPrice=5000
        @Query("SELECT p FROM Product p WHERE " +
                        "(:materialIds IS NULL OR p.material.materialId IN :materialIds) AND " +
                        "(:metallicColorIds IS NULL OR EXISTS (SELECT 1 FROM p.metallicColors mc WHERE mc.metallicColorId IN :metallicColorIds)) AND "
                        +
                        "(:categoryId IS NULL OR p.category.categoryId = :categoryId) AND " +
                        "(:isMale IS NULL OR p.male = :isMale) AND " +
                        "(:minPrice IS NULL OR :maxPrice IS NULL OR p.price BETWEEN :minPrice AND :maxPrice)")
        List<Product> findAllByFilters(
                        @Param("materialIds") List<Long> materialIds,
                        @Param("metallicColorIds") List<Long> metallicColorIds,
                        @Param("isMale") Boolean isMale,
                        @Param("minPrice") BigDecimal minPrice,
                        @Param("maxPrice") BigDecimal maxPrice,
                        @Param("categoryId") Long categoryId);

}