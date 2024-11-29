package com.example.jewelryWeb.config;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.jewelryWeb.models.Entity.Category;
import com.example.jewelryWeb.models.Entity.Gender;
import com.example.jewelryWeb.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
        private final CategoryRepository categoryRepository;

        @Override
        public void run(String... args) throws Exception {
                // Kiểm tra nếu bảng Category trống
                if (categoryRepository.count() == 0) {
                        Category root = Category.builder()
                                .categoryName("Nhẫn")
                                .parentCategory(null)
                                .build();
                        root = categoryRepository.save(root);

                        Category maleRing = Category.builder()
                                .categoryName("Nhẫn Nam")
                                .parentCategory(root)
                                .build();

                        Category femaleRing = Category.builder()
                                .categoryName("Nhẫn Nữ")
                                .parentCategory(root)
                                .build();

                        Category proposalRing = Category.builder()
                                .categoryName("Nhẫn Cầu Hôn")
                                .parentCategory(root)
                                .build();

                        Category weddingRing = Category.builder()
                                .categoryName("Nhẫn Cưới")
                                .parentCategory(root)
                                .build();

                        Category earrings = Category.builder()
                                .categoryName("Bông Tai")
                                .parentCategory(root)
                                .build();

                        Category necklace = Category.builder()
                                .categoryName("Vòng Cổ")
                                .parentCategory(root)
                                .build();

                        Category bracelet = Category.builder()
                                .categoryName("Vòng Tay")
                                .parentCategory(root)
                                .build();

                        Category jewelryset = Category.builder()
                                .categoryName("Bộ Trang Sức")
                                .parentCategory(root)
                                .build();

                        // Lưu các category con
                        categoryRepository.saveAll(List.of(maleRing, femaleRing, proposalRing, weddingRing, earrings, necklace, bracelet, jewelryset));

                        System.out.println("Database initialized successfully with categories!");
                } else {
                        System.out.println("Categories already exist in the database. Skipping initialization.");
                }
        }
}
