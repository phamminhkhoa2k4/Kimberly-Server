package com.example.jewelryWeb.models.Entity;

import lombok.*;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String categoryName;



    @ManyToOne
    @JsonIgnore 
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> subCategories;

    @OneToMany(mappedBy = "category")
    @JsonIgnore // Ngăn không cho tuần tự hóa danh sách sản phẩm
    private Set<Product> products;
    
}