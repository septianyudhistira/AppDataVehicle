package com.seeyou.vehicledataapp.model;

import jakarta.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "vehicle_brand", nullable = false)
    private String vehicleBrand;

    @Column(name = "year_of_manufacture", nullable = false)
    @Min(value = 1900, message = "Year of manufacture must be no less than 1900.")
    @Max(value = 2024, message = "Year of manufacture must be no greater than 2024.")
    private int yearOfManufacture;

    @Column(name = "engine_capacity", nullable = false)
    private int engineCapacity;

    @Column(name = "vehicle_color", nullable = false)
    private String vehicleColor;

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    // Konstruktor default untuk generate UUID
    public Vehicle() {
        this.registrationNumber = UUID.randomUUID().toString(); // Generate UUID for registrationNumber
    }

    // Konstruktor dengan parameter untuk inisialisasi semua atribut
    public Vehicle(String registrationNumber, String ownerName, String address,
                   String vehicleBrand, int yearOfManufacture, int engineCapacity,
                   String vehicleColor, String fuelType) {
        this.registrationNumber = UUID.randomUUID().toString(); // Generate UUID for registrationNumber
        this.ownerName = ownerName;
        this.address = address;
        this.vehicleBrand = vehicleBrand;
        this.yearOfManufacture = yearOfManufacture;
        this.engineCapacity = engineCapacity;
        this.vehicleColor = vehicleColor;
        this.fuelType = fuelType;
    }

    // Getter dan Setter
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
