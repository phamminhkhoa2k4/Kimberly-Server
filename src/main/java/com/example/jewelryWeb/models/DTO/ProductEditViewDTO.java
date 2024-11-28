package com.example.jewelryWeb.models.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
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

    private String metallicColor;

    private String ringBelt;

    private String material;

    private Float discount;

    private String images;

    private Boolean isFeatured;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Optional: If you need to keep track of collections
    private Set<Long> collectionIds;
    // Mapping method from Product entity to DTO
    public static ProductEditViewDTO fromEntity(Product product) {
        return ProductEditViewDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .categoryId(product.getCategory().getCategoryId())
                .price(product.getPrice())
                .metallicColor(product.getMetallicColor())
                .ringBelt(product.getRingBelt())
                .material(product.getMaterial())
                .discount(product.getDiscount())
                .images(product.getImages())
                .isFeatured(product.getIsFeatured())
                .isActive(product.getIsActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}