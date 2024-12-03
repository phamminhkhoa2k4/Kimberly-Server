package com.example.jewelryWeb.service;

import com.example.jewelryWeb.models.DTO.ItemDTO;
import com.example.jewelryWeb.models.DTO.ProductKDTO;
import com.example.jewelryWeb.models.DTO.ProductMapper;
import com.example.jewelryWeb.models.Entity.Diamond;
import com.example.jewelryWeb.models.Entity.Product;
import com.example.jewelryWeb.repository.DiamondRepository;
import com.example.jewelryWeb.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }




        public List<ProductKDTO> searchItems(String name) {
            return productRepository.findByProductNameContainingIgnoreCase(name)
                    .stream()
                    .map(ProductMapper::toProductKDTO)
                    .collect(Collectors.toList());
        }
}