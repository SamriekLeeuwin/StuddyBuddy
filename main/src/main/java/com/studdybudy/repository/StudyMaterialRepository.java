package com.studdybudy.repository;

import com.studdybudy.model.StudyMaterial;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, String> {
    boolean existsById(String title, String description);

     boolean existsByTitleAndDescription( String title, String description);


}
