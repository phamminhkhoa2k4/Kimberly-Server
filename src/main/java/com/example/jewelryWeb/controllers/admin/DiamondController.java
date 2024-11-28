package com.example.jewelryWeb.controllers.admin;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.jewelryWeb.models.Entity.*;
import com.example.jewelryWeb.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/diamonds")
public class DiamondController {
    @Autowired
    private DiamondService diamondService;

    @GetMapping
    public ResponseEntity<List<Diamond>> getAllDiamonds() {
        List<Diamond> diamonds = diamondService.getAllDiamonds();
        return ResponseEntity.ok(diamonds);
    }

    // Lấy diamond theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Diamond> getDiamondById(@PathVariable Long id) {
        return diamondService.getDiamondById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Thêm diamond mới
    @PostMapping
    public ResponseEntity<Diamond> addDiamond(@RequestBody Diamond diamond) {
        Diamond newDiamond = diamondService.addDiamond(diamond);
        return ResponseEntity.ok(newDiamond);
    }

    // Cập nhật diamond
    @PutMapping("/{id}")
    public ResponseEntity<Diamond> updateDiamond(@PathVariable Long id, @RequestBody Diamond diamond) {
        try {
            Diamond updatedDiamond = diamondService.updateDiamond(id, diamond);
            return ResponseEntity.ok(updatedDiamond);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa diamond
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiamond(@PathVariable Long id) {
        diamondService.deleteDiamond(id);
        return ResponseEntity.noContent().build();
    }
}