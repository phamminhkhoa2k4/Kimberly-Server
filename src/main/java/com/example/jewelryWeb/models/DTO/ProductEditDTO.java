package com.example.jewelryWeb.models.DTO;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEditDTO {
    private String productName;
    private Long categoryId;
    private BigDecimal price;
    private String metallicColor;
    private String ringBelt;
    private String material;
    private Float discount;
    private List<MultipartFile> images;
    private List<Long> existingImages;
    private Boolean isFeatured;
    private Boolean isActive;
}