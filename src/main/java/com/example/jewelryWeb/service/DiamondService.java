package com.example.jewelryWeb.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.jewelryWeb.models.DTO.DiamondFilterDTO;
import com.example.jewelryWeb.models.Entity.Diamond;
import com.example.jewelryWeb.repository.DiamondRepository;

@Service
public class DiamondService {
@Autowired
    private DiamondRepository diamondRepository;

    // Lấy tất cả diamond
    public List<Diamond> getAllDiamonds() {
        return diamondRepository.findAll();
    }

    // Lấy diamond theo ID
    public Optional<Diamond> getDiamondById(Long diamondId) {
        return diamondRepository.findById(diamondId);
    }

    // Thêm diamond mới
    public Diamond addDiamond(Diamond diamond) {
        return diamondRepository.save(diamond);
    }

    // Cập nhật diamond
    public Diamond updateDiamond(Long diamondId, Diamond updatedDiamond) {
        return diamondRepository.findById(diamondId).map(diamond -> {
            diamond.setWeight(updatedDiamond.getWeight());
            diamond.setSize(updatedDiamond.getSize());
            diamond.setColorGrade(updatedDiamond.getColorGrade());
            diamond.setClarity(updatedDiamond.getClarity());
            diamond.setCutting(updatedDiamond.getCutting());
            diamond.setShape(updatedDiamond.getShape());
            diamond.setPrice(updatedDiamond.getPrice());
            return diamondRepository.save(diamond);
        }).orElseThrow(() -> new IllegalArgumentException("Diamond not found with id: " + diamondId));
    }

    // Xóa diamond
    public void deleteDiamond(Long diamondId) {
        diamondRepository.deleteById(diamondId);
    }
    public List<Diamond> filterDiamonds(Double minCarat, Double maxCarat, Double minSize, Double maxSize, BigDecimal minPrice,BigDecimal maxPrice,String shape,String clarity,String color,String sortBy) {
            Specification<Diamond> spec = Specification.where(DiamondSpecification.hasShape(shape))
                .and(DiamondSpecification.hasClarity(clarity))
                .and(DiamondSpecification.hasColor(color))
                .and(DiamondSpecification.isWithinPriceRange(minPrice.multiply(BigDecimal.valueOf(100000)), maxPrice.multiply(BigDecimal.valueOf(100000))))
                .and(DiamondSpecification.isWithinCaratRange(minCarat, maxCarat))
                .and(DiamondSpecification.isWithinSizeRange(minSize, maxSize));

        Sort sort = Sort.unsorted();
        if (sortBy != null) {
            sort = switch (sortBy) {
                case "Mới Nhất" -> Sort.by(Sort.Direction.DESC, "createdAt");
                case "Giá Cao Đến Thấp" -> Sort.by(Sort.Direction.DESC, "price");
                case "Giá Thấp Đến Cao" -> Sort.by(Sort.Direction.ASC, "price");
                default -> sort;
            };
        }

        return diamondRepository.findAll(spec);
    }
}