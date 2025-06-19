package com.jura_stefanovic.zavrsni.manager;

import com.jura_stefanovic.zavrsni.constants.Role;
import com.jura_stefanovic.zavrsni.model.entity.User;
import com.jura_stefanovic.zavrsni.repository.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserManager {
    private final UserRepo userRepo;

    public UserManager(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public List<User> findAllByRole(Role role) {
        return userRepo.findAllByRole(role);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Optional<User> findByUsername(String admin) {
        return userRepo.findByUsername(admin);
    }

    public User getRandomTrainer() {
        return userRepo.getRandomTrainer(Role.TRAINER);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
}
