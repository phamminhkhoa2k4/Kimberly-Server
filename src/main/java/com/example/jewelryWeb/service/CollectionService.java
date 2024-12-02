package com.example.jewelryWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jewelryWeb.models.DTO.CollectionDTO;
import com.example.jewelryWeb.models.Entity.Collection;
import com.example.jewelryWeb.models.Entity.ImageData;
import com.example.jewelryWeb.models.Entity.Product;
import com.example.jewelryWeb.repository.CollectionRepository;
import com.example.jewelryWeb.repository.ProductRepository;
import com.example.jewelryWeb.repository.StorageRepository;
import com.example.jewelryWeb.util.ImageUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StorageRepository imageDataRepository;

    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    public Collection saveCollection(CollectionDTO dto) throws IOException {
        ImageData imageData = imageDataRepository.save(
                ImageData.builder()
                        .name(dto.getImage().getOriginalFilename())
                        .type(dto.getImage().getContentType())
                        .imageData(ImageUtils.compressImage(dto.getImage().getBytes()))
                        .build());
        ImageData bannerData = imageDataRepository.save(
                ImageData.builder()
                        .name(dto.getBanner().getOriginalFilename())
                        .type(dto.getBanner().getContentType())
                        .imageData(ImageUtils.compressImage(dto.getBanner().getBytes()))
                        .build());
        ImageData avatarData = imageDataRepository.save(
                ImageData.builder()
                        .name(dto.getAvatar().getOriginalFilename())
                        .type(dto.getAvatar().getContentType())
                        .imageData(ImageUtils.compressImage(dto.getAvatar().getBytes()))
                        .build());
        Set<Product> products = findProductsByIds(dto.getProductId());
        Collection collection = Collection.builder()
                .collectionId(dto.getCollectionId())
                .name(dto.getName())
                .description(dto.getDescription())
                .image(imageData.getId())
                .banner(bannerData.getId())
                .avatar(avatarData.getId())
                .products(products)
                .isActive(dto.getIsActive())
                .build();
        return collectionRepository.save(collection);
    }

    public Set<Product> findProductsByIds(List<Integer> productIds) {
        List<Long> longProductIds = productIds.stream()
                .map(Integer::longValue)
                .toList();
        List<Product> productList = productRepository.findAllById(longProductIds);
        return new HashSet<>(productList);
    }
    public Collection editCollection(Long collectionId, CollectionDTO dto) throws IOException {
        Collection existingCollection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new IllegalArgumentException("Collection not found"));
    
        // Update image only if a new image is provided
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            imageDataRepository.deleteById(existingCollection.getImage());
            ImageData newImageData = imageDataRepository.save(
                    ImageData.builder()
                            .name(dto.getImage().getOriginalFilename())
                            .type(dto.getImage().getContentType())
                            .imageData(ImageUtils.compressImage(dto.getImage().getBytes()))
                            .build());
            existingCollection.setImage(newImageData.getId());
        }
    
        // Update banner only if a new banner is provided
        if (dto.getBanner() != null && !dto.getBanner().isEmpty()) {
            imageDataRepository.deleteById(existingCollection.getBanner());
            ImageData newBannerData = imageDataRepository.save(
                    ImageData.builder()
                            .name(dto.getBanner().getOriginalFilename())
                            .type(dto.getBanner().getContentType())
                            .imageData(ImageUtils.compressImage(dto.getBanner().getBytes()))
                            .build());
            existingCollection.setBanner(newBannerData.getId());
        }
    
        // Update avatar only if a new avatar is provided
        if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
            imageDataRepository.deleteById(existingCollection.getAvatar());
            ImageData newAvatarData = imageDataRepository.save(
                    ImageData.builder()
                            .name(dto.getAvatar().getOriginalFilename())
                            .type(dto.getAvatar().getContentType())
                            .imageData(ImageUtils.compressImage(dto.getAvatar().getBytes()))
                            .build());
            existingCollection.setAvatar(newAvatarData.getId());
        }
    
        // Update other fields
        Set<Product> updatedProducts = findProductsByIds(dto.getProductId());
        existingCollection.setName(dto.getName());
        existingCollection.setDescription(dto.getDescription());
        existingCollection.setProducts(updatedProducts);
        existingCollection.setIsActive(dto.getIsActive());
    
        return collectionRepository.save(existingCollection);
    }
    public void deleteCollection(Long collectionId) {
        Collection existingCollection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new IllegalArgumentException("Collection not found"));
        imageDataRepository.deleteById(existingCollection.getImage());
        imageDataRepository.deleteById(existingCollection.getBanner());
        imageDataRepository.deleteById(existingCollection.getAvatar());
        collectionRepository.deleteById(collectionId);
    }
    public Optional<Collection> findById(long id){
        return collectionRepository.findById(id);
    }
}