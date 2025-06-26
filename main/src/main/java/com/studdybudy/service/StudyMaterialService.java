package com.studdybudy.service;

import com.studdybudy.dto.CreateMaterialDTO;
import com.studdybudy.dto.MaterialResponseDTO;
import com.studdybudy.model.FileUpload;
import com.studdybudy.model.StudyMaterial;
import com.studdybudy.model.User;
import com.studdybudy.repository.FileUploadRepository;
import com.studdybudy.repository.StudyMaterialRepository;
import com.studdybudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudyMaterialService {

    private final StudyMaterialRepository studyMaterialRepository;
    private final UserRepository userRepository;
    private final FileUploadRepository fileUploadRepository;


    /**
     * Creates a new study material and saves it in the database.
     * Accepts only title and description (from DTO) and returns same DTO (no ID).
     */
    public CreateMaterialDTO create(CreateMaterialDTO createMaterialDTO) {
        if (createMaterialDTO.title().isBlank() || createMaterialDTO.description().isBlank()) {
            throw new IllegalArgumentException("Title and description are required");
        }

        StudyMaterial material = new StudyMaterial(
                createMaterialDTO.title(),
                createMaterialDTO.description()
        );

        StudyMaterial saved = studyMaterialRepository.save(material);

        return new CreateMaterialDTO(saved.getTitle(), saved.getDescription());
    }

    /**
     * Returns a list of all materials as MaterialResponseDTOs, including their IDs.
     */
    public List<MaterialResponseDTO> getAll() {
        return studyMaterialRepository.findAll().stream()
                .map(material -> new MaterialResponseDTO(
                        material.getId(),
                        material.getTitle(),
                        material.getDescription()
                ))
                .toList();
    }

    /**
     * Gets a single material by its ID and returns a full response DTO.
     */
    public MaterialResponseDTO getById(Long id) {
        StudyMaterial material = studyMaterialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));

        return new MaterialResponseDTO(material.getId(), material.getTitle(), material.getDescription());
    }

    /**
     * Updates an existing material based on ID and new title/description.
     */
    public MaterialResponseDTO update(Long id, CreateMaterialDTO dto) {
        StudyMaterial material = studyMaterialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));

        material.setTitle(dto.title());
        material.setDescription(dto.description());

        StudyMaterial updated = studyMaterialRepository.save(material);

        return new MaterialResponseDTO(updated.getId(), updated.getTitle(), updated.getDescription());
    }

    public void uploadFile(Long materialId, MultipartFile file) {
        StudyMaterial material = studyMaterialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));

        String folder = "uploads/";
        try {
            Files.createDirectories(Paths.get(folder));

            String filePath = folder + UUID.randomUUID() + "_" + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            FileUpload upload = FileUpload.builder()
                    .fileName(file.getOriginalFilename())
                    .filePath(filePath)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .uploadDate(LocalDateTime.now())
                    .studyMaterial(material)
                    .uploadedBy(getAuthenticatedUser())
                    .build();

            fileUploadRepository.save(upload);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Deletes a material by its ID. Does nothing if not found.
     */
    public void delete(Long id) {
        studyMaterialRepository.deleteById(id);
    }
}
