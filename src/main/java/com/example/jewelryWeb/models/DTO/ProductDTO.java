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
    private List<Long> metallicColorIds; 
    private long ringBelt;
    private Long materialId; 
    private Float discount;
    private List<MultipartFile> images;
    private Boolean isFeatured;
    private Boolean isActive;
    private Boolean isIncludeMasterDiamond;
    private Long shapeId; 
    private Boolean isMale; 
}