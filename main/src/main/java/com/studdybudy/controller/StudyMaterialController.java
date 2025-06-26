package com.studdybudy.controller;

import com.studdybudy.dto.CreateMaterialDTO;
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
    public ResponseEntity<CreateMaterialDTO> create(@RequestBody CreateMaterialDTO createMaterialDTO) {
        CreateMaterialDTO created = service.create(createMaterialDTO);
        return ResponseEntity.ok(created);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
