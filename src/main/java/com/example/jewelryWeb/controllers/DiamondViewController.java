package com.example.jewelryWeb.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.jewelryWeb.models.DTO.DiamondFilterDTO;
import com.example.jewelryWeb.models.Entity.*;
import com.example.jewelryWeb.service.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/diamonds")
public class DiamondViewController {
    @Autowired
    private DiamondService diamondService;

    @GetMapping
    public ResponseEntity<List<Diamond>> getAllDiamonds() {
        List<Diamond> diamonds = diamondService.getAllDiamonds();
        return ResponseEntity.ok(diamonds);
    }

    // {
    //     "minWeight": 0.5,
    //     "maxWeight": 1.0,
    //     "minPrice": 1000000,
    //     "maxPrice": 5000000,
    //     "size": "4.3 - 4.9",
    //     "colorGrade": "D",
    //     "clarity": "VVS1"
    // }
    @GetMapping("/filter")
    public ResponseEntity<List<Diamond>> filterDiamonds(
            @RequestParam(required = false) String minCarat,
            @RequestParam(required = false) String maxCarat,
            @RequestParam(required = false) String minSize,
            @RequestParam(required = false) String maxSize,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String shape,
            @RequestParam(required = false) String clarity,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String sortBy
    ) {
        List<Diamond> diamonds = diamondService.filterDiamonds(
                minCarat != null ? Double.parseDouble(minCarat) : null,
                maxCarat != null ? Double.parseDouble(maxCarat) : null,
                minSize != null ? Double.parseDouble(minSize) : null,
                maxSize != null ? Double.parseDouble(maxSize) : null,
                minPrice,
                maxPrice,
                shape,
                clarity,
                color,
                sortBy
        );
        return ResponseEntity.ok(diamonds);
    }

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

}
