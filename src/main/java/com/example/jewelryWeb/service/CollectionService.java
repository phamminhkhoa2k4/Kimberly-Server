package com.example.jewelryWeb.service;


import com.example.jewelryWeb.models.Entity.ImageData;
import com.example.jewelryWeb.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jewelryWeb.models.DTO.CollectionDTO;
import com.example.jewelryWeb.models.Entity.Collection;
import com.example.jewelryWeb.models.Entity.Product;
import com.example.jewelryWeb.repository.CollectionRepository;
import com.example.jewelryWeb.repository.ProductRepository;
import com.example.jewelryWeb.repository.StorageRepository;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
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

}