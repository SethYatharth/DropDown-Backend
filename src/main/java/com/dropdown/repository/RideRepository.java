package com.dropdown.repository;

import com.dropdown.entity.Ride;
import com.dropdown.entity.RideStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface RideRepository extends JpaRepository<Ride, String> {

    @Modifying
    @Transactional
    @Query("update Ride r set r.rideStatus = :rideStatus where r.id = :id")
    void updateRideStatus(String id, RideStatus rideStatus);

}
