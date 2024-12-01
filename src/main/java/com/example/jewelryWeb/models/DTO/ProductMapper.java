package com.example.jewelryWeb.models.DTO;

import java.util.stream.Collectors;

import com.example.jewelryWeb.models.Entity.MetallicColor;
import com.example.jewelryWeb.models.Entity.Product;

public class ProductMapper {

    public static ProductKDTO toProductKDTO(Product product) {
        if (product == null) {
            return null;
        }

        return ProductKDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .category(product.getCategory()) 
                .price(product.getPrice())
                .metallicColor(product.getMetallicColors() != null
                        ? product.getMetallicColors().stream()
                              .map(MetallicColor::getColorName) 
                              .collect(Collectors.joining(", "))
                        : null)
                .ringBelt(product.getRingBelt() != null ? product.getRingBelt().getBeltType() : null) 
                .material(product.getMaterial() != null ? product.getMaterial().getMaterialName() : null) 
                .discount(product.getDiscount())
                .images(product.getImages())
                .isFeatured(product.getIsFeatured())
                .isActive(product.getIsActive())
                .isIncludeMasterDiamond(product.getIsIncludeMasterDiamond())
                .Shape(product.getShape() != null ? product.getShape().getShapeName() : null) 
                .gender(product.isMale() ? "Male" : "Female")
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}