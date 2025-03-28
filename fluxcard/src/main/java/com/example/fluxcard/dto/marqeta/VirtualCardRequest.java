package com.example.fluxcard.dto.marqeta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VirtualCardRequest {

    @JsonProperty("user_token")
    private String userToken;

    @JsonProperty("card_product_token")
    private String cardProductToken;

    private String token;

    // States: ACTIVE, INACTIVE, etc.
    private String state;

    // Optional: specify if this is a tokenized virtual card for wallet provisioning
    @JsonProperty("fulfillment")
    private Fulfillment fulfillment;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fulfillment {
        @JsonProperty("payment_instrument")
        private String paymentInstrument = "VIRTUAL_PAN"; // explicitly virtual
    }
}
