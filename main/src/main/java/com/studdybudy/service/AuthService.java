package com.studdybudy.service;

import com.studdybudy.dto.AuthResponseDTO;
import com.studdybudy.dto.UserRequestDTO;
import com.studdybudy.model.Role;
import com.studdybudy.model.User;
import com.studdybudy.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        User newUser = new User(
                request.username(),
                hashedPassword,
                Role.USER
        );

        userRepository.save(newUser);

        String token = jwtService.generateToken(newUser);

        return new AuthResponseDTO(token, newUser.getUsername());
    }

    public AuthResponseDTO login(UserRequestDTO request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(token, user.getUsername()
        );
    }
}
