package com.example.jewelryWeb.models.DTO;
import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDTO {
    private String title;
    private String contentHeader;
    private MultipartFile image;
    private String contentFooter;
    private LocalDateTime publishedAt;
    private Boolean isActive;
}