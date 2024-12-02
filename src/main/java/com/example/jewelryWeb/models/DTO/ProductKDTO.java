package com.example.jewelryWeb.models.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;
import com.example.jewelryWeb.models.Entity.Category;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductKDTO {
    private Long productId;
    private String productName;
    private Category category;
    private BigDecimal price;
    private String metallicColor;
    private String ringBelt;
    private String material;
    private Float discount;
    private String images;
    private Boolean isFeatured;
    private Boolean isActive;
    private Boolean isIncludeMasterDiamond;
    private String Shape;
    private String gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
   
}