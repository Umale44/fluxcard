package com.example.fluxcard.service;

import com.example.fluxcard.dto.marqeta.CardProductRequest;
import com.example.fluxcard.dto.marqeta.CardProductRequest.Config;
import com.example.fluxcard.dto.marqeta.CardProductRequest.Fulfillment;
import com.example.fluxcard.dto.marqeta.CardProductRequest.POI;
import com.example.fluxcard.dto.marqeta.CardProductRequest.Other;
import com.example.fluxcard.dto.marqeta.CardProductRequest.CardLifeCycle;
import com.example.fluxcard.dto.marqeta.VirtualCardRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class MarqetaCardService {

    @Value("${marqeta.api.base-url}")
    private String baseUrl;

    @Value("${marqeta.api.application-token}")
    private String appToken;

    @Value("${marqeta.api.admin-access-token}")
    private String adminToken;

    private RestTemplate restTemplate = new RestTemplate();

    public String createVirtualCard(String userToken) {
        String url = baseUrl + "/cards";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = appToken + ":" + adminToken;
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));

        VirtualCardRequest payload = new VirtualCardRequest(
                userToken,
                "fluxcard_virtual",
                "card_" + userToken,
                "ACTIVE",
                new VirtualCardRequest.Fulfillment("VIRTUAL_PAN")
        );

        HttpEntity<VirtualCardRequest> request = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create virtual card: " + response.getBody());
        }

        return response.getBody();
    }

    public String createCardProduct() {
        String url = baseUrl + "/cardproducts";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = appToken + ":" + adminToken;
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));

        CardLifeCycle lifeCycle = new CardLifeCycle(true);
        Fulfillment fulfillment = new Fulfillment("VIRTUAL_PAN");
        Other other = new Other(false, false, false, true);
        POI poi = new POI(true, false, false, other);
        Config config = new Config(lifeCycle, fulfillment, poi);

        CardProductRequest payload = new CardProductRequest(
                "FluxCard Virtual Product",
                "fluxcard_virtual",
                "2025-01-01",
                true,
                config
        );

        HttpEntity<CardProductRequest> request = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create card product: " + response.getBody());
        }

        return response.getBody();
    }
}
