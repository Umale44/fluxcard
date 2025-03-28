package com.example.fluxcard.service;

import com.example.fluxcard.dto.marqeta.MarqetaUserRequest;
import com.example.fluxcard.model.User;
import com.example.fluxcard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@Service
public class MarqetaUserService {

    @Value("${marqeta.api.base-url}")
    private String baseUrl;

    @Value("${marqeta.api.application-token}")
    private String appToken;

    @Value("${marqeta.api.admin-access-token}")
    private String adminToken;

    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;

    public MarqetaUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUserOnMarqeta(User user) {
        String url = baseUrl + "/users";

        HttpHeaders headers = buildHeaders();

        MarqetaUserRequest payload = new MarqetaUserRequest(
                "user_" + user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getGender(),
                user.getDob().toString(),
                user.getAddress(),
                true
        );

        HttpEntity<MarqetaUserRequest> request = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create user on Marqeta: " + response.getBody());
        }
    }

    public void syncUnsyncedUsers() {
        List<User> unsyncedUsers = userRepository.findByMarqetaTokenIsNull();

        for (User user : unsyncedUsers) {
            try {
                String marqetaToken = "user_" + user.getId();

                MarqetaUserRequest payload = new MarqetaUserRequest(
                        marqetaToken,
                        user.getName(),
                        user.getSurname(),
                        user.getEmail(),
                        user.getGender(),
                        user.getDob().toString(),
                        user.getAddress(),
                        true
                );

                HttpHeaders headers = buildHeaders();
                HttpEntity<MarqetaUserRequest> request = new HttpEntity<>(payload, headers);

                ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/users", request, String.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    user.setMarqetaToken(marqetaToken);
                    userRepository.save(user);
                } else {
                    System.err.println("Failed to sync user " + user.getId() + ": " + response.getBody());
                }
            } catch (Exception e) {
                System.err.println("Error syncing user " + user.getId() + ": " + e.getMessage());
            }
        }
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = appToken + ":" + adminToken;
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
        return headers;
    }
}
