package com.dropdown.APICalls;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NominatimResponse {
    @JsonProperty("address")
    private Address address;

    @Data
    public static class Address {
        private String city;
        private String town;
        private String village;
        private String county;
        private String state;
        private String country;
    }
}