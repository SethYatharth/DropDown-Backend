package com.dropdown.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class GPSLocation {
    private double latitude;
    private double longitude;
    private String cellAddress;
}
