package com.example.jewelryWeb.models.DTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private String productName;
    private Long categoryId;
    private BigDecimal price;
    private String metallicColor;
    private String ringBelt;
    private String material;
    private Float discount;
    private List<MultipartFile> images;
    private Boolean isFeatured;
    private Boolean isActive;
    private Boolean isIncludeMasterDiamond;
    private String shape;
    private String gender;
}
