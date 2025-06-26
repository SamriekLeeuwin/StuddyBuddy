package com.studdybudy.controller;

import com.studdybudy.dto.CreateMaterialDTO;
import com.studdybudy.dto.MaterialResponseDTO;
import com.studdybudy.service.StudyMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class StudyMaterialController {

    private final StudyMaterialService service;

    // Create a new material
    @PostMapping
    public ResponseEntity<CreateMaterialDTO> create(@RequestBody CreateMaterialDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // Get all materials
    @GetMapping
    public ResponseEntity<List<MaterialResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Get material by ID
    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // Update material by ID
    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> update(@PathVariable Long id, @RequestBody CreateMaterialDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        service.uploadFile(id, file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    // Delete material by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
