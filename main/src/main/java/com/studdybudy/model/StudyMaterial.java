package com.studdybudy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString(exclude = "user")
public class StudyMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100)
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 1000)
    @Column(nullable = false, length = 1000)
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate uploadDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public StudyMaterial(String title,  String description) {
    }

    public StudyMaterial() {

    }
}
