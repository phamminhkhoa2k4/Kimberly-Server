package com.example.jewelryWeb.controllers.admin;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.util.stream.Collectors;

import com.example.jewelryWeb.models.DTO.CollectionDTO;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.jewelryWeb.models.DTO.CollectionDTO;
import com.example.jewelryWeb.models.Entity.*;
import com.example.jewelryWeb.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/collections")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @GetMapping
    public ResponseEntity<List<Collection>> getAllCollections() {
        List<Collection> collections = collectionService.getAllCollections();
        return ResponseEntity.ok(collections);
    }

    @GetMapping("/{id}")
    Optional<Collection> getCollection(@PathVariable Long id) {
        return collectionService.findById(id);
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Collection> createCollection(@ModelAttribute CollectionDTO collectionDTO) throws IOException {
        Collection savedCollection = collectionService.saveCollection(collectionDTO);
        return ResponseEntity.ok(savedCollection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Collection> updateCollection(@PathVariable Long id, @ModelAttribute CollectionDTO collectionDTO)
            throws Throwable {
        Collection updatedCollection = collectionService.editCollection(id, collectionDTO);
        return updatedCollection != null ? ResponseEntity.ok(updatedCollection) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public void deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
    }
    // @PostMapping("/upload-image")
    // public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile
    // file) throws IOException {
    // String response = storageService.uploadImage(file);
    // return ResponseEntity.ok(response);
    // }
}