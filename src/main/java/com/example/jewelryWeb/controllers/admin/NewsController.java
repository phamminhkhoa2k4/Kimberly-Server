package com.example.jewelryWeb.controllers.admin;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.example.jewelryWeb.models.DTO.NewsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.jewelryWeb.models.Entity.*;
import com.example.jewelryWeb.service.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/admin/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

   

    // Lấy tất cả tin tức
    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        List<News> newsList = newsService.getAllNews();
        return ResponseEntity.ok(newsList);
    }

    // Lấy tin tức theo ID
    @GetMapping("/{id}")
    public Optional<News> getNewsById(@PathVariable Long id) throws Exception {
        return newsService.getNewsById(id);
    }

    // Thêm tin tức mới
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createNews(@ModelAttribute NewsDTO newsDTO) {
        try {
            News news = newsService.createNews(newsDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(news);
        } catch (Exception e) {
            if ("Title already exists.".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Title already exists."));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // Cập nhật tin tức
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @ModelAttribute NewsDTO newsDTO) {
        try {
            News updatedNews = newsService.updateNews(id, newsDTO);
            return ResponseEntity.ok(updatedNews);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "News not found."));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to process image."));
        } catch (Exception e) {
            if ("Title already exists.".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Title already exists."));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // Xóa tin tức
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable Long id) throws Exception {
        try {
            newsService.deleteNewsById(id);
            return ResponseEntity.ok("News deleted successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}