package com.example.jewelryWeb.models.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.jewelryWeb.models.Entity.Collection;
import com.example.jewelryWeb.models.Entity.MetallicColor;
import com.example.jewelryWeb.models.Entity.Product;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEditViewDTO {
    private Long productId;
    private String productName;
    private Long categoryId; // Changed from Category to categoryId
    private BigDecimal price;
    private Set<MetallicColor> metallicColorIds; // List of IDs for metallic colors
    private String ringBelt;
    private Long materialId; // Material ID instead of String
    private Float discount;
    private List<String> images; // List of image paths
    private Boolean isFeatured;
    private Boolean isActive;
    private Long shapeId; // Shape ID instead of String
    private String gender; // "Male" or "Female" based on the `male` field
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Collection> collectionIds; // IDs of associated collections

    // Mapping method from Product entity to DTO
    // public static ProductEditViewDTO fromEntity(Product product) {
    //     return ProductEditViewDTO.builder()
    //             .productId(product.getProductId())
    //             .productName(product.getProductName())
    //             .categoryId(product.getCategory().getCategoryId())
    //             .price(product.getPrice())
    //             .metallicColorIds(product.getMetallicColors())
    //             .ringBelt(product.getRingBelt().)
    //             .materialId(product.getMaterial().getMaterialId()) // Map material to its ID
    //             .discount(product.getDiscount())
    //             .images(Arrays.asList(product.getImages().split(","))) // Split images into a list
    //             .isFeatured(product.getIsFeatured())
    //             .isActive(product.getIsActive())
    //             .shapeId(product.getShape().getShapeId()) // Map shape to its ID
    //             .gender(product.isMale() ? "Male" : "Female") // Determine gender
    //             .createdAt(product.getCreatedAt())
    //             .updatedAt(product.getUpdatedAt())
    //             .collectionIds(product.getCollections())
    //             .build();
    // }
}
