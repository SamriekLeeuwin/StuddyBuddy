package com.studdybudy.controller;

import com.studdybudy.model.StudyMaterial;
import com.studdybudy.repository.StudyMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //
@RequestMapping("/api/materials") //
@RequiredArgsConstructor //
public class StudyMaterialController {

    private final StudyMaterialRepository materialRepository;

    @GetMapping
    public ResponseEntity<List<StudyMaterial>> getAll() {
        List<StudyMaterial> materials = materialRepository.findAll();
        return ResponseEntity.ok(materials);
    }

    @PostMapping
    public ResponseEntity<StudyMaterial> create(@RequestBody StudyMaterial material) {
        StudyMaterial saved = materialRepository.save(material);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        materialRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
