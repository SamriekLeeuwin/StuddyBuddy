package com.studdybudy.controller;

import com.studdybudy.model.StudyMaterial;
import com.studdybudy.service.studyMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class StudyMaterialController {

    private final studyMaterialService service;

    @GetMapping
    public ResponseEntity<List<StudyMaterial>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<StudyMaterial> create(@RequestBody StudyMaterial material) {
        return ResponseEntity.ok(service.create(material));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
