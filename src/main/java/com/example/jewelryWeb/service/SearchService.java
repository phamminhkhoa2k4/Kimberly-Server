package com.example.jewelryWeb.service;

import com.example.jewelryWeb.models.DTO.ItemDTO;
import com.example.jewelryWeb.models.Entity.Diamond;
import com.example.jewelryWeb.models.Entity.Product;
import com.example.jewelryWeb.repository.DiamondRepository;
import com.example.jewelryWeb.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SearchService {

    private final ProductRepository productRepository;
    private final DiamondRepository diamondRepository;

    public SearchService(ProductRepository productRepository, DiamondRepository diamondRepository) {
        this.productRepository = productRepository;
        this.diamondRepository = diamondRepository;
    }

    public List<ItemDTO> searchItems(String name) {
        List<ItemDTO> products = productRepository.findAll().stream()
                .filter(product -> product.getProductName().toLowerCase().contains(name.toLowerCase()))
                .map(product -> new ItemDTO(product.getProductName(), product.getPrice(),product.getImages()))
                .collect(Collectors.toList());

        List<ItemDTO> diamonds = diamondRepository.findAll().stream()
                .filter(diamond -> diamond.getDiamondName().toLowerCase().contains(name.toLowerCase()))
                .map(diamond -> new ItemDTO(diamond.getDiamondName(), diamond.getPrice(),"diamondImg"))
                .collect(Collectors.toList());

        return Stream.concat(products.stream(), diamonds.stream())
                .collect(Collectors.toList());
    }
}