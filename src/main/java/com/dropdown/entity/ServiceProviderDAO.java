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
    String vehicleType;
    String vehicleNo;
    String vehicleModel;
    double latitude;
    double longitude;


    public ServiceProviderDAO(String id, String name, String email, String phoneNo,String vehicleType,String vehicleNo,String vehicleModel, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.vehicleType = vehicleType;
        this.vehicleNo = vehicleNo;
        this.vehicleModel = vehicleModel;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
