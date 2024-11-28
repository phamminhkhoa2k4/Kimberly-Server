package com.example.jewelryWeb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jewelryWeb.models.Entity.ImageData;

public interface StorageRepository extends JpaRepository<ImageData,Long> {


    Optional<ImageData> findByName(String fileName);

    // String uploadImage(MultipartFile image);
}
