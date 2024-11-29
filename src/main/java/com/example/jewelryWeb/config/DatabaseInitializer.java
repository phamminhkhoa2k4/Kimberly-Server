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
                                Category.builder().categoryName("Nhẫn Cầu Hôn").parentCategory(null)
                                                .build(),
                                Category.builder().categoryName("Nhẫn Cưới").parentCategory(null).build(),
                                Category.builder().categoryName("Bông Tai").parentCategory(null).build(),
                                Category.builder().categoryName("Vòng Cổ").parentCategory(null).build(),
                                Category.builder().categoryName("Vòng Tay").parentCategory(null).build(),
                                Category.builder().categoryName("Bộ Trang Sức").parentCategory(null)
                                                .build());
                Optional<Category> rootCategoryOpt2 = categoryRepository.findById(2L);
                if (rootCategoryOpt2.isEmpty()) {
                        for (Category subCategory : subCategories) {
                                if (categoryRepository.findByCategoryNameAndParentCategory(
                                                subCategory.getCategoryName(), rootCategory).isEmpty()) {
                                        categoryRepository.save(subCategory);
                                        System.out.println("Created sub-category: " + subCategory.getCategoryName());
                                }
                        }
                }

                System.out.println("Database initialization complete!");
        }
}
