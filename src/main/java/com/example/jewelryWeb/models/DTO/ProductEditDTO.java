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
    private List<Long> metallicColorIds; // IDs of metallic colors
    private long ringBelt;
    private Long materialId; // ID of the material
    private Float discount;
    private List<MultipartFile> newImages; // Images being uploaded
    private List<Long> existingImages; // IDs of already uploaded images
    private Boolean isFeatured;
    private Boolean isActive;
    private Long shapeId; // ID of the shape
    private Boolean isMale; // Male or Female
}
