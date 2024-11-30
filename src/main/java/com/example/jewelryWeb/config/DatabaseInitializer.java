package com.example.jewelryWeb.config;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.jewelryWeb.models.Entity.Category;
import com.example.jewelryWeb.models.Entity.Gender;
import com.example.jewelryWeb.models.Entity.Shape;
import com.example.jewelryWeb.models.Entity.Material;
import com.example.jewelryWeb.models.Entity.MetallicColor;
import com.example.jewelryWeb.models.Entity.RingBelt;
import com.example.jewelryWeb.repository.CategoryRepository;
import com.example.jewelryWeb.repository.ShapeRepository;
import com.example.jewelryWeb.repository.MaterialRepository;
import com.example.jewelryWeb.repository.MetallicColorRepository;
import com.example.jewelryWeb.repository.RingBeltRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

        private final CategoryRepository categoryRepository;
        private final ShapeRepository shapeRepository;
        private final MaterialRepository materialRepository;
        private final MetallicColorRepository metallicColorRepository;
        private final RingBeltRepository ringBeltRepository;

        @Override
        public void run(String... args) throws Exception {

                // Initialize Categories
                Optional<Category> rootCategoryOpt = categoryRepository.findById(1L);
                Category rootCategory;

                if (rootCategoryOpt.isEmpty()) {
                        rootCategory = Category.builder()
                                        .categoryName("Nhẫn")
                                        .parentCategory(null)
                                        .build();
                        rootCategory = categoryRepository.save(rootCategory);
                } else {
                        rootCategory = rootCategoryOpt.get();
                }

                List<Category> subCategories = List.of(
                                Category.builder().categoryName("Nhẫn Cầu Hôn").parentCategory(rootCategory).build(),
                                Category.builder().categoryName("Nhẫn Cưới").parentCategory(rootCategory).build(),
                                Category.builder().categoryName("Bông Tai").parentCategory(rootCategory).build(),
                                Category.builder().categoryName("Vòng Cổ").parentCategory(rootCategory).build(),
                                Category.builder().categoryName("Vòng Tay").parentCategory(rootCategory).build(),
                                Category.builder().categoryName("Bộ Trang Sức").parentCategory(rootCategory).build());

                for (Category subCategory : subCategories) {
                        if (categoryRepository.findByCategoryNameAndParentCategory(subCategory.getCategoryName(),
                                        rootCategory).isEmpty()) {
                                categoryRepository.save(subCategory);
                                System.out.println("Created sub-category: " + subCategory.getCategoryName());
                        }
                }

                // Initialize Shape
                if (shapeRepository.findById(1L).isEmpty()) { 
                        List<Shape> shapes = List.of(
                                        Shape.builder().shapeName("Tròn").build(),
                                        Shape.builder().shapeName("Vuông").build(),
                                        Shape.builder().shapeName("Oval").build(),
                                        Shape.builder().shapeName("Trái Tim").build());

                        shapeRepository.saveAll(shapes); 
                        System.out.println("Initialized shapes.");
                } else {
                        System.out.println("Shapes already initialized.");
                }

                // Initialize Material
                if (materialRepository.findById(1L).isEmpty()) {
                        List<Material> materials = List.of(
                                        Material.builder().materialName("Vàng 18k").build(),
                                        Material.builder().materialName("Hk").build());

                        materialRepository.saveAll(materials);
                        System.out.println("Initialized materials.");
                } else {
                        System.out.println("Materials already initialized.");
                }

                // Initialize MetallicColor
                if (metallicColorRepository.findById(1L).isEmpty()) { // Kiểm tra dữ liệu đầu tiên
                        List<MetallicColor> metallicColors = List.of(
                                        MetallicColor.builder().colorName("Vàng Trắng").build(),
                                        MetallicColor.builder().colorName("Vàng Chanh").build(),
                                        MetallicColor.builder().colorName("Vàng Hồng").build());

                        metallicColorRepository.saveAll(metallicColors); // Lưu toàn bộ danh sách
                        System.out.println("Initialized metallic colors.");
                } else {
                        System.out.println("Metallic colors already initialized.");
                }
 
                // Initialize RingBelt
                if (ringBeltRepository.findById(1L).isEmpty()) { // Kiểm tra dữ liệu đầu tiên
                        List<RingBelt> ringBelts = List.of(
                                        RingBelt.builder().beltType("Đai Trơn").build(),
                                        RingBelt.builder().beltType("Đai Nhám").build(),
                                        RingBelt.builder().beltType("Đai Đính Xoàn").build());

                        ringBeltRepository.saveAll(ringBelts); // Lưu toàn bộ danh sách
                        System.out.println("Initialized ring belts.");
                } else {
                        System.out.println("Ring belts already initialized.");
                }
                System.out.println("Database initialization complete!");
        }
}
