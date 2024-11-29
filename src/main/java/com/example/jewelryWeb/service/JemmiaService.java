package com.example.jewelryWeb.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.jewelryWeb.models.DTO.JemmiaDTO;
import com.example.jewelryWeb.models.Entity.ImageData;
import com.example.jewelryWeb.models.Entity.Jemmia;
import com.example.jewelryWeb.repository.JemmiaRepository;
import com.example.jewelryWeb.repository.StorageRepository;
import com.example.jewelryWeb.util.ImageUtils;
@Service
public class JemmiaService {
    @Autowired
    private JemmiaRepository jemmiaRepository;
    @Autowired
    private StorageRepository imageDataRepository;
    public Jemmia createJemmia(JemmiaDTO jemmiaDTO) throws Exception {
        if (jemmiaRepository.existsByTitle(jemmiaDTO.getTitle())) {
            throw new Exception("Title already exists.");
        }
        ImageData imageData = imageDataRepository.save(
                ImageData.builder()
                        .name(jemmiaDTO.getImage().getOriginalFilename())
                        .type(jemmiaDTO.getImage().getContentType())
                        .imageData(ImageUtils.compressImage(jemmiaDTO.getImage().getBytes()))
                        .build());



        ImageData thumbnailData = imageDataRepository.save(
                ImageData.builder()
                        .name(jemmiaDTO.getThumbnail().getOriginalFilename())
                        .type(jemmiaDTO.getThumbnail().getContentType())
                        .imageData(ImageUtils.compressImage(jemmiaDTO.getThumbnail().getBytes()))
                        .build());
        Jemmia jemmia = Jemmia.builder()
                .title(jemmiaDTO.getTitle())
                .contentHeader(jemmiaDTO.getContentHeader())
                .contentFooter(jemmiaDTO.getContentFooter())
                .startDate(jemmiaDTO.getStartDate())
                .endDate(jemmiaDTO.getEndDate())
                .publishedAt(jemmiaDTO.getPublishedAt())
                .isActive(jemmiaDTO.getIsActive())
                .image(imageData.getId())
                .thumbnail(thumbnailData.getId())
                .build();
        return jemmiaRepository.save(jemmia);
    }
    public Jemmia updateJemmia(Long id, JemmiaDTO jemmiaDTO) throws Exception {
        Jemmia existingJemmia = jemmiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jemmia not found."));
        if (!existingJemmia.getTitle().equals(jemmiaDTO.getTitle()) && jemmiaRepository.existsByTitle(jemmiaDTO.getTitle())) {
            throw new Exception("Title already exists.");
        }
        ImageData imageData = null;
        if (jemmiaDTO.getImage() != null && !jemmiaDTO.getImage().isEmpty()) {
            imageData = imageDataRepository.save(
                    ImageData.builder()
                            .name(jemmiaDTO.getImage().getOriginalFilename())
                            .type(jemmiaDTO.getImage().getContentType())
                            .imageData(ImageUtils.compressImage(jemmiaDTO.getImage().getBytes()))
                            .build());
        }
        // Xử lý cập nhật ảnh thumbnail
        ImageData thumbnailData = null;
        if (jemmiaDTO.getThumbnail() != null && !jemmiaDTO.getThumbnail().isEmpty()) {
            thumbnailData = imageDataRepository.save(
                    ImageData.builder()
                            .name(jemmiaDTO.getThumbnail().getOriginalFilename())
                            .type(jemmiaDTO.getThumbnail().getContentType())
                            .imageData(ImageUtils.compressImage(jemmiaDTO.getThumbnail().getBytes()))
                            .build());
        }
        existingJemmia.setTitle(jemmiaDTO.getTitle());
        existingJemmia.setContentHeader(jemmiaDTO.getContentHeader());
        existingJemmia.setContentFooter(jemmiaDTO.getContentFooter());
        existingJemmia.setStartDate(jemmiaDTO.getStartDate());
        existingJemmia.setEndDate(jemmiaDTO.getEndDate());
        existingJemmia.setPublishedAt(jemmiaDTO.getPublishedAt());
        existingJemmia.setIsActive(jemmiaDTO.getIsActive());
        if (imageData != null) {
            existingJemmia.setImage(imageData.getId());
        }


        // Cập nhật ID ảnh thumbnail nếu có thay đổi
        if (thumbnailData != null) {
            existingJemmia.setThumbnail(thumbnailData.getId());
        }

        return jemmiaRepository.save(existingJemmia);
    }
    public Jemmia getJemmiaById(Long id) {
        return jemmiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jemmia not found."));
    }
    public void deleteJemmia(Long id) {
        Jemmia existingJemmia = jemmiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jemmia not found."));
        imageDataRepository.deleteById(existingJemmia.getImage());
        jemmiaRepository.delete(existingJemmia);
    }
}