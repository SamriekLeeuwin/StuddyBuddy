package com.studdybudy.controller;

import com.studdybudy.dto.AuthResponseDTO;
import com.studdybudy.dto.UserRequestDTO;
import com.studdybudy.service.AuthService;
import com.studdybudy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// the controller for the authentication process.
@RestController
@RequestMapping("api/auth")
public class AuthController {


     private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
     private final UserService userService;
     private final AuthService authService;

@Autowired
     public  AuthController(UserService userService, AuthService authService){
          this.userService= userService;
          this.authService = authService;
     }


     @PostMapping("/login")
     public ResponseEntity<AuthResponseDTO> login(@RequestBody UserRequestDTO request) {
          logger.info("Login attempt for user: {}", request.username());
          AuthResponseDTO response = userService.login(request);
          logger.info("Login successful for user: {}", request.username());
          return ResponseEntity.ok(response);
     }

     // eindpoint for t
     @PostMapping("/signup")
     public ResponseEntity<AuthResponseDTO> register(@RequestBody UserRequestDTO request) {
          logger.info("Registering user: {}", request.username());
          AuthResponseDTO response = userService.createUser(request);
          logger.info("User registered: {}", request.username());
          return new ResponseEntity<>(response, HttpStatus.CREATED);
     }
}

