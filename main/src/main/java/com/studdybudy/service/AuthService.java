package com.studdybudy.service;

import com.studdybudy.dto.AuthResponseDTO;
import com.studdybudy.dto.UserRequestDTO;
import com.studdybudy.model.Role;
import com.studdybudy.model.User;
import com.studdybudy.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.studdybudy.config.AppConfig;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO createUser(UserRequestDTO request) {
        System.out.println("[DEBUG] Signup attempt for username: " + request.username());

        boolean exists = userRepository.findByUsername(request.username()).isPresent();
        System.out.println("[DEBUG] User already exists? " + exists);

        if (exists) {
            throw new IllegalArgumentException("Username already exists!");
        }

        System.out.println("[DEBUG] Encoding password...");
        String hashedPassword = passwordEncoder.encode(request.password());

        User newUser = new User(
                request.username(),
                hashedPassword,
                Role.USER
        );

        System.out.println("[DEBUG] Saving user to database...");
        User savedUser = userRepository.save(newUser);
        System.out.println("[DEBUG] User saved with ID: " + savedUser.getId());

        System.out.println("[DEBUG] Generating JWT token...");
        String token = jwtService.generateToken(savedUser);

        System.out.println("[DEBUG] Signup complete.");
        return new AuthResponseDTO(token, savedUser.getUsername());
    }


    public AuthResponseDTO login(UserRequestDTO request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        System.out.println("Creating user with username: " + request.username());

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(token, user.getUsername()
        );
    }
    public User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

}
