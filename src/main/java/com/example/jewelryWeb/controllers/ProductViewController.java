package com.example.jewelryWeb.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.jewelryWeb.models.DTO.ItemDTO;
import com.example.jewelryWeb.models.DTO.ProductKDTO;
import com.example.jewelryWeb.models.DTO.ProductMapper;
import com.example.jewelryWeb.models.Entity.*;
import com.example.jewelryWeb.service.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/product")
public class ProductViewController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SearchService searchService;
    @GetMapping("/{id}")
public ResponseEntity<ProductKDTO> getProductById(@PathVariable Long id) {
    Product product = productService.getProductById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    ProductKDTO productKDTO = ProductMapper.toProductKDTO(product);
    return ResponseEntity.ok(productKDTO);
}
 @GetMapping("/search")
    public List<ItemDTO> searchItems(@RequestParam String name) {
        return searchService.searchItems(name);
    }
// @GetMapping("/{id}")
// public ResponseEntity<Product> getProductById(@PathVariable Long id) {
//     Product product = productService.getProductById(id)
//             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
//     return ResponseEntity.ok(product);
// }
    @GetMapping("ring")
    public ResponseEntity<List<ProductKDTO>> getAllRing() {
        List<Product> products = productService.getProductsByCategoryId(1L);
        List<ProductKDTO> productKDTOs = products.stream()
                .map(ProductMapper::toProductKDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productKDTOs);
    }

    @GetMapping("ring/male")
    public ResponseEntity<List<ProductKDTO>> getAllMaleRing() {
        List<Product> products = productService.getProductsByCategoryIdAndGender(1L, true);
        List<ProductKDTO> productKDTOs = products.stream()
                .map(ProductMapper::toProductKDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productKDTOs);
    }

    @GetMapping("ring/female")
    public ResponseEntity<List<ProductKDTO>> getAllFemaleRing() {
        List<Product> products = productService.getProductsByCategoryIdAndGender(1L, false);
        List<ProductKDTO> productKDTOs = products.stream()
                .map(ProductMapper::toProductKDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productKDTOs);
    }

    @GetMapping("ring/proposal")
    public ResponseEntity<List<ProductKDTO>> getAllProposalRing() {
        List<Product> products = productService.getProductsByCategoryId(2L);
        List<ProductKDTO> productKDTOs = products.stream()
                .map(ProductMapper::toProductKDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productKDTOs);
    }

    @GetMapping("ring/wedding")
    public ResponseEntity<List<ProductKDTO>> getAllWeddingRing() {
        List<Product> products = productService.getProductsByCategoryId(3L);
        List<ProductKDTO> productKDTOs = products.stream()
                .map(ProductMapper::toProductKDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productKDTOs);
    }

    @GetMapping("bracelet")
    public ResponseEntity<List<ProductKDTO>> getAllBracelet() {
        List<Product> products = productService.getProductsByCategoryId(4L);
        List<ProductKDTO> productKDTOs = products.stream()
                .map(ProductMapper::toProductKDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productKDTOs);
    }

    @GetMapping("necklace")
    public ResponseEntity<List<ProductKDTO>> getAllNecklace() {
        List<Product> products = productService.getProductsByCategoryId(5L);
        List<ProductKDTO> productKDTOs = products.stream()
                .map(ProductMapper::toProductKDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productKDTOs);
    }

    @GetMapping("earrings")
    public ResponseEntity<List<ProductKDTO>> getAllEarrings() {
        List<Product> products = productService.getProductsByCategoryId(6L);
        List<ProductKDTO> productKDTOs = products.stream()
                .map(ProductMapper::toProductKDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productKDTOs);
    }

    @GetMapping("jewelryset")
    public ResponseEntity<List<ProductKDTO>> getAllJewelrySet() {
        List<Product> products = productService.getProductsByCategoryId(7L);
        List<ProductKDTO> productKDTOs = products.stream()
                .map(ProductMapper::toProductKDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productKDTOs);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductKDTO>> filterProducts(
            @RequestParam(required = false) List<String> materials,
            @RequestParam(required = false) List<String> metallicColors,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        List<Product> products = productService.filterProducts(materials, metallicColors, gender, minPrice, maxPrice, categoryName, sortBy);
        List<ProductKDTO> productKDTOs = products.stream()
                .map(ProductMapper::toProductKDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productKDTOs);
    }

    @GetMapping("/related/{id}")
    public ResponseEntity<List<ProductKDTO>> getRelatedProducts(@PathVariable Long id) {
        List<Product> relatedProducts = productService.getRelatedProducts(id);
        List<ProductKDTO> productKDTOs = relatedProducts.stream()
                .map(ProductMapper::toProductKDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productKDTOs);
    }

    @GetMapping("/material")
    public List<Material> getMaterials() {
        return productService.getMaterials();
    }

    @GetMapping("/metallicColors")
    public List<MetallicColor> getMetallicColors() {
        return productService.getMetallicColors();
    }

    @GetMapping("/shape")
    public List<Shape> getShapes() {
        return productService.getShapes();
    }

    @GetMapping("/ringbelt")
    public List<RingBelt> getRingBelts() {
        return productService.getRingBelts();
    }

    @GetMapping("/category")
    public List<Category> getCategories() {
        return productService.getCategories();
    }
}
