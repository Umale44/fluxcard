package com.example.fluxcard.dto.marqeta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardProductRequest {

    private String name;
    private String token;

    @JsonProperty("start_date")
    private String startDate;

    private boolean active;
    private Config config;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config {

        @JsonProperty("card_life_cycle")
        private CardLifeCycle cardLifeCycle;

        private Fulfillment fulfillment;
        private POI poi;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardLifeCycle {

        @JsonProperty("activate_upon_issue")
        private boolean activateUponIssue;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fulfillment {

        @JsonProperty("payment_instrument")
        private String paymentInstrument;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class POI {
        private boolean ecommerce;

        @JsonProperty("manual_entry")
        private boolean manualEntry;

        private boolean atm;
        private Other other;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Other {

        @JsonProperty("cardholder_presence_required")
        private boolean cardholderPresenceRequired;

        @JsonProperty("card_presence_required")
        private boolean cardPresenceRequired;

        @JsonProperty("use_static_pin")
        private boolean useStaticPin;

        private boolean allow;
    }
}