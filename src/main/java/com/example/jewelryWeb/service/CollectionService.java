package com.example.jewelryWeb.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jewelryWeb.models.DTO.CollectionDTO;
import com.example.jewelryWeb.models.Entity.Collection;
import com.example.jewelryWeb.models.Entity.Product;
import com.example.jewelryWeb.repository.CollectionRepository;
import com.example.jewelryWeb.repository.ProductRepository;
import com.example.jewelryWeb.repository.StorageRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private ProductRepository productRepository;
    // @Autowired
    // private StorageRepository imageDataRepository;
    // Lấy danh sách tất cả các collection


    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    //     public Collection saveCollection(CollectionDTO dto) {
// String imageName = imageDataRepository.uploadImage(dto.getImage());
//     String bannerName = imageDataRepository.uploadImage(dto.getBanner());
//     String avatarName = imageDataRepository.uploadImage(dto.getAvatar());
//     Set<Product> products = productRepository.findAllById(dto.getProductId())
//                                              .stream()
//                                              .collect(Collectors.toSet());
//     Collection collection = Collection.builder()
//         .collectionId(dto.getCollectionId())
//         .name(dto.getName())
//         .description(dto.getDescription())
//         .image(imageName)
//         .banner(bannerName)
//         .avatar(avatarName)
//         .products(products)
//         .isActive(dto.getIsActive())
//         .build();
//     return collectionRepository.save(collection);
// }

}