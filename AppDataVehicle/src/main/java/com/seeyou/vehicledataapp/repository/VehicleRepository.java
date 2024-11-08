package com.seeyou.vehicledataapp.repository;

import com.seeyou.vehicledataapp.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String > {
    List<Vehicle> findByRegistrationNumberContainingIgnoreCase(String registrationNumber);
    List<Vehicle> findByOwnerNameContainingIgnoreCase(String ownerName);

}
