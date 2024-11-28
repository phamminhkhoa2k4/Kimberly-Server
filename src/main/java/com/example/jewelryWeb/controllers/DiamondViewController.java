package com.example.jewelryWeb.controllers;

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
    @PostMapping("/filter")
    public ResponseEntity<List<Diamond>> filterDiamonds(@RequestBody DiamondFilterDTO filterDTO) {
        List<Diamond> diamonds = diamondService.filterDiamonds(filterDTO);
        return ResponseEntity.ok(diamonds);
    }
    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}
