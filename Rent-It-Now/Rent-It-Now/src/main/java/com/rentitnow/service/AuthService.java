// AuthService.java
package com.rentitnow.service;

import com.rentitnow.model.User;
import com.rentitnow.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    private final Set<String> loggedInUsers = new HashSet<>(); // tracks usernames

    public AuthService(UserRepository userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            return "❌ Username already taken";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "✅ User registered successfully!";
    }

    public String login(String username, String password) {
        Optional<User> optionalUser = userRepo.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                loggedInUsers.add(username); // store as logged in
                return "✅ Login successful!";
            }
        }
        return "❌ Invalid username or password";
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<String> getLoggedInUsers() {
        return new ArrayList<>(loggedInUsers);
    }

    public String logout(String username) {
        if (loggedInUsers.remove(username)) {
            return "✅ Logout successful";
        }
        return "❌ User not logged in";
    }
}