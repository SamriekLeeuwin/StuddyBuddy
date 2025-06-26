package com.studdybudy.repository;

import com.studdybudy.model.StudyMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing StudyMaterial entities.
 * Extends JpaRepository to provide basic CRUD operations.
 */
@Repository
public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {

    /**
     * Checks whether a material with the same title and description already exists.
     * Spring Data automatically generates the query based on the method name.
     *
     *  Order of parameters MUST match the order in method name.
     */
    boolean existsByTitleAndDescription(String title, String description);
}
