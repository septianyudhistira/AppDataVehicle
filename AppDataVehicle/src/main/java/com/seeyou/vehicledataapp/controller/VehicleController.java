package com.seeyou.vehicledataapp.controller;

import com.seeyou.vehicledataapp.model.Vehicle;
import com.seeyou.vehicledataapp.repository.VehicleRepository;
import com.seeyou.vehicledataapp.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    // Error message class untuk menangani pesan error
    public class ErrorMessage {
        private String message;

        public ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleRepository vehicleRepository;

    // Metode untuk menambah kendaraan
    @PostMapping
    public ResponseEntity<?> addVehicle(@Valid @RequestBody Vehicle vehicle) {

        // Validasi untuk registrationNumber
        if (vehicle.getRegistrationNumber() == null || vehicle.getRegistrationNumber().isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Registration number is required."));
        }

        // Mengecek apakah nomor registrasi sudah ada di database
        if (vehicleRepository.existsById(vehicle.getRegistrationNumber())) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Registration number already exists."));
        }

        // Validasi untuk yearOfManufacture
        if (vehicle.getYearOfManufacture() < 1900 || vehicle.getYearOfManufacture() > 2024) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Year of manufacture must be between 1900 and 2024."));
        }

        try {
            // Simpan kendaraan dengan menggunakan vehicleRepository
            Vehicle savedVehicle = vehicleRepository.save(vehicle);
            return ResponseEntity.ok(savedVehicle);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage("Error saving vehicle data: " + e.getMessage()));
        }
    }

    // Mengambil semua kendaraan
    @GetMapping("/all")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    // Mengambil kendaraan berdasarkan nomor registrasi
    @GetMapping("/{registrationNumber}")
    public Vehicle getVehicleByRegistrationNumber(@PathVariable String registrationNumber) {
        return vehicleService.getVehicleByRegistrationNumber(registrationNumber);
    }

    // Menghapus kendaraan berdasarkan nomor registrasi
    @DeleteMapping("/{registrationNumber}")
    public void deleteVehicle(@PathVariable String registrationNumber) {
        vehicleService.deleteVehicle(registrationNumber);
    }

    // Memperbarui data kendaraan berdasarkan nomor registrasi
    @PutMapping("/{registrationNumber}")
    public ResponseEntity<?> updateVehicle(@PathVariable String registrationNumber, @RequestBody Vehicle updatedVehicle) {

        // Cek apakah kendaraan dengan nomor registrasi tersebut ada
        Vehicle existingVehicle = vehicleRepository.findById(registrationNumber).orElse(null);
        if (existingVehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
        }

        // Perbarui data kendaraan
        existingVehicle.setOwnerName(updatedVehicle.getOwnerName());
        existingVehicle.setAddress(updatedVehicle.getAddress());
        existingVehicle.setVehicleBrand(updatedVehicle.getVehicleBrand());
        existingVehicle.setYearOfManufacture(updatedVehicle.getYearOfManufacture());
        existingVehicle.setEngineCapacity(updatedVehicle.getEngineCapacity());
        existingVehicle.setVehicleColor(updatedVehicle.getVehicleColor());
        existingVehicle.setFuelType(updatedVehicle.getFuelType());

        // Simpan perubahan
        vehicleRepository.save(existingVehicle);
        return ResponseEntity.ok(existingVehicle);
    }

    // Mengambil kendaraan berdasarkan nomor registrasi atau nama pemilik
    @GetMapping("/search")
    public List<Vehicle> searchVehicles(
            @RequestParam(required = false) String registrationNumber,
            @RequestParam(required = false) String ownerName) {

        // Cari kendaraan berdasarkan nomor registrasi atau nama pemilik
        if (registrationNumber != null && !registrationNumber.isEmpty()) {
            return vehicleRepository.findByRegistrationNumberContainingIgnoreCase(registrationNumber);
        } else if (ownerName != null && !ownerName.isEmpty()) {
            return vehicleRepository.findByOwnerNameContainingIgnoreCase(ownerName);
        } else {
            return vehicleRepository.findAll();
        }
    }
}
