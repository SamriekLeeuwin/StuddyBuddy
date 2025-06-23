package com.studdybudy.controller;

import com.studdybudy.dto.AuthResponseDTO;
import com.studdybudy.dto.UserRequestDTO;
import com.studdybudy.service.AuthService;
import com.studdybudy.service.UserService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// the controller for the authentication process.
@RestController
@RequestMapping("api/auth")
public class AuthController {

     private final AuthService authService;

     public AuthController(AuthService authService) {
          this.authService = authService;
     }

     @PostMapping("/signup")
     public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody UserRequestDTO request) {
          AuthResponseDTO response = authService.createUser(request);
          return new ResponseEntity<>(response, HttpStatus.CREATED);
     }

     @PostMapping("/login")
     public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UserRequestDTO request) {
          AuthResponseDTO response = authService.login(request);
          return ResponseEntity.ok(response);
     }
}
