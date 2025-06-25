// AuthController.java
package com.rentitnow.controller;

import com.rentitnow.model.User;
import com.rentitnow.service.AuthService;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return ResponseEntity.ok(authService.login(user.getUsername(), user.getPassword()));
    }
 // Get all registered users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    // Get currently logged-in usernames
    @GetMapping("/loggedin")
    public ResponseEntity<List<String>> getLoggedInUsers() {
        return ResponseEntity.ok(authService.getLoggedInUsers());
    }

    // Logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        return ResponseEntity.ok(authService.logout(username));
    }


}