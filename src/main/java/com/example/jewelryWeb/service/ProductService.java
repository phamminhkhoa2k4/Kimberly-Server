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
import com.example.jewelryWeb.models.Entity.Product;
import com.example.jewelryWeb.repository.CategoryRepository;
import com.example.jewelryWeb.repository.ProductRepository;
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

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    // Thêm sản phẩm mới
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Cập nhật sản phẩm
    public Product updateProduct(Long productId, Product updatedProduct) {
        return productRepository.findById(productId).map(product -> {
            product.setProductName(updatedProduct.getProductName());
            product.setCategory(updatedProduct.getCategory());
            product.setPrice(updatedProduct.getPrice());
            product.setMetallicColor(updatedProduct.getMetallicColor());
            product.setMaterial(updatedProduct.getMaterial());
            product.setDiscount(updatedProduct.getDiscount());
            product.setImages(updatedProduct.getImages());
            product.setIsFeatured(updatedProduct.getIsFeatured());
            product.setIsActive(updatedProduct.getIsActive());
            return productRepository.save(product);
        }).orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }

    public Product createProduct(ProductDTO productDTO) throws IOException {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Category not found with ID: " + productDTO.getCategoryId()));
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

        // Tạo Product
        Product product = Product.builder()
                .productName(productDTO.getProductName())
                .category(category)
                .price(productDTO.getPrice())
                .metallicColor(productDTO.getMetallicColor())
                .ringBelt(productDTO.getRingBelt())
                .material(productDTO.getMaterial())
                .discount(productDTO.getDiscount())
                .images(String.join(",", imageIds))
                .isFeatured(productDTO.getIsFeatured())
                .isActive(productDTO.getIsActive())
                .Shape(productDTO.getShape())
                .isIncludeMasterDiamond(productDTO.getIsIncludeMasterDiamond())
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

    public Product editProduct(Long productId, ProductDTO productDTO) throws IOException {
        // Tìm sản phẩm theo ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        // Lấy danh sách ảnh hiện tại từ sản phẩm
        Set<ImageData> currentImages = new HashSet<>();
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            List<Long> currentImageIds = Arrays.stream(product.getImages().split(","))
                    .map(Long::valueOf)
                    .toList();
            currentImages.addAll(imageDataRepository.findAllById(currentImageIds));
        }

        // Danh sách ảnh mới từ DTO
        Set<ImageData> newImages = new HashSet<>();
        Set<String> newImageNames = new HashSet<>();
        if (productDTO.getImages() != null) {
            for (MultipartFile file : productDTO.getImages()) {
                String newName = file.getOriginalFilename();
                newImageNames.add(newName);

                // Kiểm tra nếu tên ảnh đã tồn tại
                Optional<ImageData> existingImage = currentImages.stream()
                        .filter(img -> img.getName().equals(newName))
                        .findFirst();

                if (existingImage.isPresent()) {
                    // Ảnh đã tồn tại, giữ lại
                    newImages.add(existingImage.get());
                } else {
                    // Ảnh mới, thêm vào cơ sở dữ liệu
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

        // Xóa ảnh không còn trong danh sách mới
        for (ImageData currentImage : currentImages) {
            if (!newImageNames.contains(currentImage.getName())) {
                boolean isReferenced = productRepository.existsByImageId(currentImage.getId().toString());
                if (isReferenced) {
                    imageDataRepository.deleteById(currentImage.getId());
                }
            }
        }

        // Cập nhật các thuộc tính của sản phẩm
        product.setProductName(productDTO.getProductName());
        product.setCategory(
                categoryRepository.findById(productDTO.getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Category not found with ID: " + productDTO.getCategoryId())));
        product.setPrice(productDTO.getPrice());
        product.setMetallicColor(productDTO.getMetallicColor());
        product.setRingBelt(productDTO.getRingBelt());
        product.setMaterial(productDTO.getMaterial());
        product.setDiscount(productDTO.getDiscount());
        product.setIsIncludeMasterDiamond(productDTO.getIsIncludeMasterDiamond());
        product.setShape(productDTO.getShape());
        Set<String> finalImageIds = newImages.stream()
                .map(image -> String.valueOf(image.getId()))
                .collect(Collectors.toSet());
        product.setImages(String.join(",", finalImageIds));

        product.setIsFeatured(productDTO.getIsFeatured());
        product.setIsActive(productDTO.getIsActive());

        // Lưu sản phẩm sau khi chỉnh sửa
        return productRepository.save(product);
    }

    public List<Product> filterProducts(List<String> materials, List<String> metallicColor, String gender, BigDecimal minPrice,
            BigDecimal maxPrice, String categoryName ,String sortBy) {
        Specification<Product> spec = Specification.where(ProductSpecification.hasMaterial(materials))
                .and(ProductSpecification.hasCategory(categoryName))
                .and(ProductSpecification.hasMetallicColor(metallicColor))
                .and(ProductSpecification.hasGender(gender))
                .and(ProductSpecification.isWithinPriceRange(minPrice.multiply(BigDecimal.valueOf(1000000)), maxPrice.multiply(BigDecimal.valueOf(1000000))));

        Sort sort = Sort.unsorted();
        if (sortBy != null) {
            sort = switch (sortBy) {
                case "Mới Nhất" -> Sort.by(Sort.Direction.DESC, "createdAt");
                case "Giá Cao Đến Thấp" -> Sort.by(Sort.Direction.DESC, "price");
                case "Giá Thấp Đến Cao" -> Sort.by(Sort.Direction.ASC, "price");
                default -> sort;
            };
        }

        return productRepository.findAll(spec,sort);
    }

    public List<Product> getRelatedProducts(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Category category = product.getCategory();

        Set<Product> relatedProducts = new HashSet<>();
        relatedProducts.addAll(productRepository.findRelatedProductsByCategory(category, productId));
        relatedProducts.addAll(productRepository.findRelatedProductsByMetallicColor(product.getMetallicColor(), productId));
        relatedProducts.addAll(productRepository.findRelatedProductsByMaterial(product.getMaterial(), productId));

        return new ArrayList<>(relatedProducts);
    }


    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }


    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }
}