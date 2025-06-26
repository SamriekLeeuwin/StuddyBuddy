package com.studdybudy.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateMaterialDTO(
        @NotBlank String title,
        @NotBlank String description
) {}
