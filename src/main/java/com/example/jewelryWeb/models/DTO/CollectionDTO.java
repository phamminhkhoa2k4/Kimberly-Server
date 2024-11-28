package com.example.jewelryWeb.models.DTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionDTO {
    private Long collectionId;
    private String name;
    private String description;
    private MultipartFile image;
    private MultipartFile banner;
    private MultipartFile avatar;
    private List<Integer> productId;
    private Boolean isActive;
}