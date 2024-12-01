package com.example.jewelryWeb.controllers.admin;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.example.jewelryWeb.models.DTO.JemmiaDTO;
import com.example.jewelryWeb.models.Entity.*;
import com.example.jewelryWeb.service.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/admin/jemmia")
public class JemmiaController {
    @Autowired
    private JemmiaService jemmiaService;
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createJemmia(@ModelAttribute JemmiaDTO jemmiaDTO) {
        try {
            Jemmia jemmia = jemmiaService.createJemmia(jemmiaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(jemmia);
        } catch (Exception e) {
            if ("Title already exists.".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Title already exists."));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJemmia(@PathVariable Long id, @ModelAttribute JemmiaDTO jemmiaDTO) {
        try {
            Jemmia updatedJemmia = jemmiaService.updateJemmia(id, jemmiaDTO);
            return ResponseEntity.ok(updatedJemmia);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Jemmia not found."));
        } catch (Exception e) {
            if ("Title already exists.".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Title already exists."));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getJemmiaById(@PathVariable Long id) {
        try {
            Jemmia jemmia = jemmiaService.getJemmiaById(id);
            return ResponseEntity.ok(jemmia);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Jemmia not found."));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJemmia(@PathVariable Long id) {
        try {
            jemmiaService.deleteJemmia(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Jemmia not found."));
        }
    }


    @GetMapping
    public ResponseEntity<?> getAll(){
       List<Jemmia> jemmia =  jemmiaService.getAll();
        return ResponseEntity.ok(jemmia);
    }
}