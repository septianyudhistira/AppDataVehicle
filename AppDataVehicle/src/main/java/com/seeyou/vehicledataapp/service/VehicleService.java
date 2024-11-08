package com.seeyou.vehicledataapp.service;

import com.seeyou.vehicledataapp.model.Vehicle;
import com.seeyou.vehicledataapp.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    // Menyimpan kendaraan
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // Mengambil semua kendaraan
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Mengambil kendaraan berdasarkan nomor registrasi
    public Vehicle getVehicleByRegistrationNumber(String registrationNumber) {
        return vehicleRepository.findById(registrationNumber).orElse(null);
    }

    // Menghapus kendaraan berdasarkan nomor registrasi
    public void deleteVehicle(String registrationNumber) {
        vehicleRepository.deleteById(registrationNumber);
    }
}
