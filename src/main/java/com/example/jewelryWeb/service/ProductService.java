package com.example.jewelryWeb.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.example.jewelryWeb.models.DTO.ProductEditDTO;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jewelryWeb.models.DTO.ProductDTO;
import com.example.jewelryWeb.models.Entity.Category;
import com.example.jewelryWeb.models.Entity.Gender;
import com.example.jewelryWeb.models.Entity.ImageData;
import com.example.jewelryWeb.models.Entity.Material;
import com.example.jewelryWeb.models.Entity.MetallicColor;
import com.example.jewelryWeb.models.Entity.Product;
import com.example.jewelryWeb.models.Entity.RingBelt;
import com.example.jewelryWeb.models.Entity.Shape;
import com.example.jewelryWeb.repository.CategoryRepository;
import com.example.jewelryWeb.repository.MaterialRepository;
import com.example.jewelryWeb.repository.MetallicColorRepository;
import com.example.jewelryWeb.repository.ProductRepository;
import com.example.jewelryWeb.repository.RingBeltRepository;
import com.example.jewelryWeb.repository.ShapeRepository;
import com.example.jewelryWeb.repository.StorageRepository;
import com.example.jewelryWeb.util.ImageUtils;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StorageRepository imageDataRepository;
    @Autowired
    private MetallicColorRepository metallicColorRepository;
    @Autowired
    private RingBeltRepository ringBeltRepository;
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ShapeRepository shapeRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }


    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }

    public Product createProduct(ProductDTO productDTO) throws IOException {
        if (isProductNameDuplicate(productDTO.getProductName())) {
            throw new IllegalArgumentException("Product name already exists: " + productDTO.getProductName());
        }
        List<String> imageIds = new ArrayList<>();
        if (productDTO.getImages() != null) {
            for (MultipartFile file : productDTO.getImages()) {
                ImageData imageData = imageDataRepository.save(
                        ImageData.builder()
                                .name(file.getOriginalFilename())
                                .type(file.getContentType())
                                .imageData(ImageUtils.compressImage(file.getBytes()))
                                .build());
                imageIds.add(imageData.getId().toString());
            }
        }

        Product product = Product.builder()
                .productName(productDTO.getProductName())
                .category(categoryRepository.findById(productDTO.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found"))) 
                .price(productDTO.getPrice())
                .metallicColors(new HashSet<>(metallicColorRepository.findAllById(productDTO.getMetallicColorIds()))) 
                .ringBelt(ringBeltRepository.findById(productDTO.getRingBelt())
                        .orElseThrow(() -> new RuntimeException("RingBelt not found"))) 
                .material(materialRepository.findById(productDTO.getMaterialId())
                        .orElseThrow(() -> new RuntimeException("Material not found"))) 
                .discount(productDTO.getDiscount())
                .images(String.join(",", imageIds)) 
                .isFeatured(productDTO.getIsFeatured())
                .isActive(productDTO.getIsActive())
                .isIncludeMasterDiamond(productDTO.getIsIncludeMasterDiamond())
                .shape(shapeRepository.findById(productDTO.getShapeId())
                        .orElseThrow(() -> new RuntimeException("Shape not found"))) 
                .male(productDTO.getIsMale()) 
                .build();
        return productRepository.save(product);
    }

    public void deleteProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
        if (product.getCollections() != null && !product.getCollections().isEmpty()) {
            throw new IllegalStateException("Product is part of a collection and cannot be deleted.");
        }
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            String[] imageIds = product.getImages().split(",");
            for (String imageId : imageIds) {
                imageDataRepository.deleteById(Long.valueOf(imageId));
            }
        }
        productRepository.delete(product);
    }

    public boolean isProductNameDuplicate(String productName) {
        return productRepository.existsByProductName(productName);
    }

    public Product editProduct(Long productId, ProductEditDTO productDTO) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        if (!product.getProductName().equals(productDTO.getProductName())
                && isProductNameDuplicate(productDTO.getProductName())) {
            throw new IllegalArgumentException("Product name already exists: " + productDTO.getProductName());
        }

        Set<ImageData> currentImages = new HashSet<>();
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            List<Long> currentImageIds = Arrays.stream(product.getImages().split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            currentImages.addAll(imageDataRepository.findAllById(currentImageIds));
        }

        Set<String> newImageNames = new HashSet<>();
        Set<ImageData> newImages = new HashSet<>();

        if (productDTO.getNewImages() != null) {
            for (MultipartFile file : productDTO.getNewImages()) {
                String newName = file.getOriginalFilename();
                newImageNames.add(newName);

                Optional<ImageData> existingImage = currentImages.stream()
                        .filter(img -> img.getName().equals(newName))
                        .findFirst();

                if (existingImage.isPresent()) {
                    newImages.add(existingImage.get());
                } else {
                    ImageData newImage = imageDataRepository.save(
                            ImageData.builder()
                                    .name(newName)
                                    .type(file.getContentType())
                                    .imageData(ImageUtils.compressImage(file.getBytes()))
                                    .build());
                    newImages.add(newImage);
                }
            }
        }

        for (ImageData currentImage : currentImages) {
            if (!newImageNames.contains(currentImage.getName()) &&
                    !productDTO.getExistingImages().contains(currentImage.getId())) {
                imageDataRepository.deleteById(currentImage.getId());
            }
        }

        Set<String> finalImageIds = new HashSet<>(productDTO.getExistingImages().stream()
                .map(String::valueOf)
                .collect(Collectors.toSet()));

        finalImageIds.addAll(newImages.stream()
                .map(image -> String.valueOf(image.getId()))
                .collect(Collectors.toSet()));

        product.setImages(String.join(",", finalImageIds)); 
        product.setProductName(productDTO.getProductName());
        product.setCategory(
                categoryRepository.findById(productDTO.getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Category not found with ID: " + productDTO.getCategoryId())));
        product.setPrice(productDTO.getPrice());
        product.setMetallicColors(new HashSet<>(metallicColorRepository.findAllById(productDTO.getMetallicColorIds()))); 
        product.setRingBelt(
            ringBeltRepository.findById(productDTO.getRingBelt())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Material not found with ID: " + productDTO.getMaterialId())));
        product.setMaterial(
                materialRepository.findById(productDTO.getMaterialId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Material not found with ID: " + productDTO.getMaterialId())));
        product.setDiscount(productDTO.getDiscount());
        product.setIsFeatured(productDTO.getIsFeatured());
        product.setIsActive(productDTO.getIsActive());
        product.setShape(
                shapeRepository.findById(productDTO.getShapeId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Shape not found with ID: " + productDTO.getShapeId())));
        product.setMale(productDTO.getIsMale()); // Set gender flag based on the DTO

        return productRepository.save(product);
    }

    public List<Product> filterProducts(List<String> materials, List<String> metallicColor, String gender,
            BigDecimal minPrice,
            BigDecimal maxPrice, String categoryName, String sortBy) {
        Specification<Product> spec = Specification.where(ProductSpecification.hasMaterial(materials))
                .and(ProductSpecification.hasCategory(categoryName))
                .and(ProductSpecification.hasMetallicColor(metallicColor))
                .and(ProductSpecification.hasGender(gender))
                .and(ProductSpecification.isWithinPriceRange(minPrice.multiply(BigDecimal.valueOf(1000000)),
                        maxPrice.multiply(BigDecimal.valueOf(1000000))));

        Sort sort = Sort.unsorted();
        if (sortBy != null) {
            sort = switch (sortBy) {
                case "Mới Nhất" -> Sort.by(Sort.Direction.DESC, "createdAt");
                case "Giá Cao Đến Thấp" -> Sort.by(Sort.Direction.DESC, "price");
                case "Giá Thấp Đến Cao" -> Sort.by(Sort.Direction.ASC, "price");
                default -> sort;
            };
        }

        return productRepository.findAll(spec, sort);
    }

    // public List<Product> getRelatedProducts(Long productId) {
    // Product product = productRepository.findById(productId)
    // .orElseThrow(() -> new IllegalArgumentException("Product not found"));

    // Category category = product.getCategory();

    // Set<Product> relatedProducts = new HashSet<>();
    // relatedProducts.addAll(productRepository.findRelatedProductsByCategory(category,
    // productId));
    // relatedProducts
    // .addAll(productRepository.findRelatedProductsByMetallicColor(product.getMetallicColor(),
    // productId));
    // relatedProducts.addAll(productRepository.findRelatedProductsByMaterial(product.getMaterial(),
    // productId));

    // return new ArrayList<>(relatedProducts);
    // }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }
    public List<Material> getMaterials() {
        return materialRepository.findAll();
    }
    public List<MetallicColor> getMetallicColors() {
        return metallicColorRepository.findAll();
    }
    public List<Shape> getShapes() {
        return shapeRepository.findAll();
    }
    public List<RingBelt> getRingBelts(){
        return ringBeltRepository.findAll();
    }
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }
}