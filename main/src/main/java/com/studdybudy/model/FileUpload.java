package com.studdybudy.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;

    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private StudyMaterial studyMaterial;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User uploadedBy;
}
