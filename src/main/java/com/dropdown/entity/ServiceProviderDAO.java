package com.dropdown.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ServiceProviderDAO implements Serializable {
    String id;
    String name;
    String email;
    String phoneNo;
    String role;
    double latitude;
    double longitude;
    double distance;

    public ServiceProviderDAO(String id, String name, String email, String phoneNo, String role, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.role = role;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
