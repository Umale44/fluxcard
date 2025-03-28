package com.example.fluxcard.dto.marqeta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class POI {

    @JsonProperty("ecommerce")
    private boolean ecommerce;

    @JsonProperty("atm")
    private boolean atm;

    @JsonProperty("other")
    private CardProductRequest.Other other;
}
