package com.example.fluxcard.dto.marqeta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarqetaUserRequest {

    private String token;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;
    private String gender;

    @JsonProperty("birth_date")
    private String birthDate;

    @JsonProperty("address1")
    private String address;

    private boolean active = true;
}
