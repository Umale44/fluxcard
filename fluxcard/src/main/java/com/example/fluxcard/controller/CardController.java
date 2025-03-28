package com.example.fluxcard.controller;

import com.example.fluxcard.service.MarqetaCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final MarqetaCardService marqetaCardService;

    public CardController(MarqetaCardService marqetaCardService) {
        this.marqetaCardService = marqetaCardService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVirtualCard(@RequestParam String userToken) {
        String cardResponse = marqetaCardService.createVirtualCard(userToken);
        return ResponseEntity.ok(cardResponse);
    }

    @PostMapping("/card-product")
    public ResponseEntity<String> createCardProduct() {
        String response = marqetaCardService.createCardProduct();
        return ResponseEntity.ok(response);
    }
}
