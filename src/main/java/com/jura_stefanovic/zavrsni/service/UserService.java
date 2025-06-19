package com.jura_stefanovic.zavrsni.service;

import com.jura_stefanovic.zavrsni.constants.Role;
import com.jura_stefanovic.zavrsni.dto.models.users.UserDto;
import com.jura_stefanovic.zavrsni.dto.requests.UserRequest;
import com.jura_stefanovic.zavrsni.manager.UserManager;
import com.jura_stefanovic.zavrsni.messages.ErrorMessages;
import com.jura_stefanovic.zavrsni.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserManager userManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserManager userManager, PasswordEncoder passwordEncoder) {
        this.userManager = userManager;
        this.passwordEncoder = passwordEncoder;
    }

    // Create
    public ResponseEntity<?> addUser(UserRequest request) {
        if (userManager.findByEmail(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body(ErrorMessages.EMAIL_ALREADY_IN_USE.getMessage());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userManager.save(user);

        return ResponseEntity.ok().body("User created successfully");
    }

    // Read all users
    public List<User> getAllUsers() {
        return userManager.findAllByRole(Role.USER);
    }

    // Read user by id
    public User getUserById(Long id) {
        User user = userManager.findById(id);
        if (user != null && user.getRole() == Role.USER) {
            return user;
        }
        return null;
    }

    // Update user
    public ResponseEntity<?> updateUser(Long id, UserRequest request) {
        User existingUser = userManager.findById(id);
        if (existingUser == null || existingUser.getRole() != Role.USER) {
            return ResponseEntity.badRequest().body("User not found");
        }

        existingUser.setEmail(request.getEmail());
        existingUser.setUsername(request.getUsername());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userManager.save(existingUser);
        return ResponseEntity.ok().body("User updated successfully");
    }

    // Delete user
    public ResponseEntity<?> deleteUser(Long id) {
        User existingUser = userManager.findById(id);
        if (existingUser == null || existingUser.getRole() != Role.USER) {
            return ResponseEntity.badRequest().body("User not found");
        }

        userManager.deleteById(id);
        return ResponseEntity.ok().body("User deleted successfully");
    }

    public ResponseEntity<?> getLoggedInUserData() {
        User loggedInUser = userManager.getCurrentUser();
        if (loggedInUser == null) {
            return ResponseEntity.ok().body(null);
        }
        UserDto userDto = new UserDto(loggedInUser);
        return ResponseEntity.ok().body(userDto);
    }
}
