package com.studdybudy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a User in the system.
 * It will be saved to a table called 'app_user' in the database.
 */
@Entity
@Table(name = "app_user")
@Getter
@Setter
public class User {

    // Unique ID for each user (automatically generated)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user's username (must be unique and not empty)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // The user's email address (must be valid and unique)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    // The user's password (must be between 8 and 20 characters)
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Column(name = "user_password", nullable = false)
    private String password;

    // The role of the user: USER, ADMIN, etc. (saved as text in the database)
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    /**
     * Default constructor.
     * Needed by JPA (Java Persistence API) to load data from the database.
     */
    public User() {
    }

    /**
     * Custom constructor used in your AuthService to create a new user.
     * Only needs username, password, and role for signup.
     */
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
