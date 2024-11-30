package com.example.jewelryWeb.models.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DiamondFilterDTO {
    private BigDecimal minWeight;  
    private BigDecimal maxWeight;  
    private BigDecimal minPrice;  
    private BigDecimal maxPrice;   
    private String size;       
    private String colorGrade;  
    private String clarity;
    private String shape;
}