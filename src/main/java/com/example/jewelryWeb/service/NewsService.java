package com.example.jewelryWeb.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.jewelryWeb.models.DTO.NewsDTO;
import com.example.jewelryWeb.models.Entity.ImageData;
import com.example.jewelryWeb.models.Entity.News;
import com.example.jewelryWeb.repository.NewsRepository;
import com.example.jewelryWeb.repository.StorageRepository;
import com.example.jewelryWeb.util.ImageUtils;
@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private StorageRepository imageDataRepository;


    public News createNews(NewsDTO newsDTO) throws Exception {
        if (newsRepository.existsByTitle(newsDTO.getTitle())) {
            throw new Exception("Title already exists.");
        }

        ImageData imageData = imageDataRepository.save(
                ImageData.builder()
                        .name(newsDTO.getImage().getOriginalFilename())
                        .type(newsDTO.getImage().getContentType())
                        .imageData(ImageUtils.compressImage(newsDTO.getImage().getBytes()))
                        .build());

        News news = News.builder()
                .title(newsDTO.getTitle())
                .contentHeader(newsDTO.getContentHeader())
                .contentFooter(newsDTO.getContentFooter())
                .publishedAt(newsDTO.getPublishedAt())
                .isActive(newsDTO.getIsActive())
                .image(imageData.getId())
                .build();

        newsRepository.save(news);
        return news;
    }

    // Lấy tất cả các tin tức
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    // Lấy tin tức theo ID
    public News updateNews(Long id, NewsDTO newsDTO) throws Exception {
        // Kiểm tra xem bản ghi có tồn tại không
        News existingNews = newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("News not found."));

        // Kiểm tra tiêu đề trùng lặp
        if (!existingNews.getTitle().equals(newsDTO.getTitle()) && newsRepository.existsByTitle(newsDTO.getTitle())) {
            throw new Exception("Title already exists.");
        }

        // Xử lý cập nhật hình ảnh (nếu có hình ảnh mới)
        ImageData imageData = null;
        if (newsDTO.getImage() != null && !newsDTO.getImage().isEmpty()) {
            imageData = imageDataRepository.save(
                    ImageData.builder()
                            .name(newsDTO.getImage().getOriginalFilename())
                            .type(newsDTO.getImage().getContentType())
                            .imageData(ImageUtils.compressImage(newsDTO.getImage().getBytes()))
                            .build());
        }

        // Cập nhật thông tin bài viết
        existingNews.setTitle(newsDTO.getTitle());
        existingNews.setContentHeader(newsDTO.getContentHeader());
        existingNews.setContentFooter(newsDTO.getContentFooter());
        existingNews.setPublishedAt(newsDTO.getPublishedAt());
        existingNews.setIsActive(newsDTO.getIsActive());
        if (imageData != null) {
            existingNews.setImage(imageData.getId());
        }

        return newsRepository.save(existingNews);
    }

    // Thêm tin tức mới
    public Optional<News> getNewsById(Long id) throws Exception {
        return newsRepository.findById(id);
    }

    // Delete News
    public void deleteNewsById(Long id) throws Exception {
        if (!newsRepository.existsById(id)) {
            throw new Exception("News not found.");
        }
        News news=newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("News not found with ID: " + id));
        imageDataRepository.deleteById(news.getImage());
        newsRepository.deleteById(id);
    }

    // Image Upload
    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = imageDataRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        return imageData != null ? file.getOriginalFilename() : null;
    }
}