package com.example.fluxcard.controller;

import com.example.fluxcard.model.User;
import com.example.fluxcard.repository.UserRepository;
import com.example.fluxcard.service.MarqetaUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final MarqetaUserService marqetaService;
    private final MarqetaUserService marqetaUserService;

    public UserController(UserRepository userRepository, MarqetaUserService marqetaService, MarqetaUserService marqetaUserService) {
        this.userRepository = userRepository;
        this.marqetaService = marqetaService;
        this.marqetaUserService = marqetaUserService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User saved = userRepository.save(user);
        marqetaService.createUserOnMarqeta(saved);
        return ResponseEntity.ok("User created locally and on Marqeta.");
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncUnsyncedUsers() {
        marqetaUserService.syncUnsyncedUsers();
        return ResponseEntity.ok("Sync initiated for unsynced users.");
    }
}
