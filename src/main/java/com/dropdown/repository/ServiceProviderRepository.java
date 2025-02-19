package com.dropdown.repository;

import com.dropdown.entity.ServiceProvider;
import com.dropdown.entity.ServiceProviderDAO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, String> {
    Optional<ServiceProvider> findByEmailIgnoreCase(String email);


//    @Query(value = """
//                    SELECT
//                id,
//                name,
//                email,
//                phone_no,
//                role,
//                latitude,
//                longitude,
//                (6371 * ACOS(
//                    COS(RADIANS(:latitude)) *
//                    COS(RADIANS(latitude)) *
//                    COS(RADIANS(longitude) - RADIANS(:longitude)) +
//                    SIN(RADIANS(:latitude)) *
//                    SIN(RADIANS(latitude))
//                )) AS distance
//            FROM
//                service_provider
//            WHERE
//                (6371 * ACOS(
//                    COS(RADIANS(:latitude)) *
//                    COS(RADIANS(latitude)) *
//                    COS(RADIANS(longitude) - RADIANS(:longitude)) +
//                    SIN(RADIANS(:latitude)) *
//                    SIN(RADIANS(latitude))
//                )) <= :radius
//                        and city=:city
//            ORDER BY
//                distance;""", nativeQuery = true)
//    List<ServiceProviderDAO> findNearestServiceProvider(double latitude, double longitude, double radius, String city);


    @Query(value = """
            SELECT
                id,
                            name,
                            email,
                            phone_no,
                            vehicle_type,
                            vehicle_no,
                            vehicle_model,
                            latitude,
                            longitude
                from service_provider
            where 
                cell_address = :cellAddress
            """,nativeQuery = true)
    List<ServiceProviderDAO> findAllByCellAddress(String cellAddress);



    @Query(value = """
            SELECT
                id,
                            name,
                            email,
                            phone_no,
                            vehicle_type,
                            vehicle_no,
                            vehicle_model,
                            latitude,
                            longitude
                from service_provider
            where 
                cell_address = :cellAddress
                        and 
                            city = :city
            """,nativeQuery = true)
    List<ServiceProviderDAO> findAllByCellAddressAndCity(String cellAddress,String city);


}
