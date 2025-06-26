package com.studdybudy.dto;

/**
 * DTO used to send study material data back to the client.
 * It includes the database ID, title and description.
 */
public record MaterialResponseDTO(Long id, String title, String description) {
}
