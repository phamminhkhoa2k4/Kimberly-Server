package com.example.jewelryWeb.controllers.admin;

import java.util.List;
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

<<<<<<< HEAD
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Collection> createCollection(@ModelAttribute CollectionDTO collectionDTO) throws IOException {
        Collection savedCollection = collectionService.saveCollection(collectionDTO);
        return ResponseEntity.ok(savedCollection);
    }
=======
    // Tạo mới một collection
     @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
     public ResponseEntity<Collection> createCollection(@ModelAttribute CollectionDTO collectionDTO) throws IOException {
         Collection savedCollection = collectionService.saveCollection(collectionDTO);
         return ResponseEntity.ok(savedCollection);
     }
>>>>>>> dc7f83c1b7884e901a1408689c3fe9d4e58123b5

    // @PutMapping("/{id}")
    // public ResponseEntity<CollectionDTO> updateCollection(@PathVariable Long id, @RequestBody CollectionDTO collectionDTO) {
    //     Collection updatedCollection = collectionService.updateCollection(id, collectionDTO);
    //     return updatedCollection != null ? ResponseEntity.ok(collectionService.toDTO(updatedCollection)) : ResponseEntity.notFound().build();
    // }

    // @PostMapping("/upload-image")
    // public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
    //     String response = storageService.uploadImage(file);
    //     return ResponseEntity.ok(response);
    // }
}