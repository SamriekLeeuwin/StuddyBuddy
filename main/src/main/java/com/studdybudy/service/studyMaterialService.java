package com.studdybudy.service;

import com.studdybudy.dto.CreateMaterialDTO;
import com.studdybudy.dto.MaterialResponseDTO;
import com.studdybudy.model.StudyMaterial;
import com.studdybudy.repository.StudyMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class studyMaterialService {

    // dependycy enjection
    private final StudyMaterialRepository studyMaterialRepository;


    public CreateMaterialDTO create(CreateMaterialDTO createMaterialDTO) {
        if(createMaterialDTO.description().isBlank() || createMaterialDTO.title().isBlank()) {
            throw new IllegalArgumentException("Description and title are required");

        }
         StudyMaterial studyMaterial = new StudyMaterial(
                 createMaterialDTO.title(),
                 createMaterialDTO.description()

         );

        System.out.println("[DEBUG] Saving user to database...");
        StudyMaterial savedStudyMaterial = studyMaterialRepository.save(studyMaterial);

        return new CreateMaterialDTO(savedStudyMaterial.getTitle(), savedStudyMaterial.getDescription());



        }

    public List<CreateMaterialDTO> getAll() {
        List<StudyMaterial> materials = studyMaterialRepository.findAll();

        return materials.stream()
                .map(material -> new CreateMaterialDTO(material.getTitle(), material.getDescription()))
                .toList();
    }


    public void delete(Long id) {
    }
}
