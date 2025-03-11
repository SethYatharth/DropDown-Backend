package com.dropdown.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnoreProperties("rides")
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_provider_id")
    @JsonIgnoreProperties("rides")
    private ServiceProvider serviceProvider;


    private LocalDateTime createdAt;
    private String destinationLocation;

}
