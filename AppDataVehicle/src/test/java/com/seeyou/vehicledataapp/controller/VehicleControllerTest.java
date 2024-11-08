package com.seeyou.vehicledataapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeyou.vehicledataapp.model.Vehicle;
import com.seeyou.vehicledataapp.repository.VehicleRepository;
import com.seeyou.vehicledataapp.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class VehicleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleController vehicleController;

    private Vehicle vehicle;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
        vehicle = new Vehicle("123ABC", "John Doe", "1234 Elm Street", "Toyota", 2010, 2000, "Red", "Petrol");
    }

    @Test
    public void testAddVehicle_Success() throws Exception {
        // Mocking repositori untuk simulasikan kendaraan baru
        when(vehicleRepository.existsById(anyString())).thenReturn(false);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        // Mencoba menambahkan kendaraan
        mockMvc.perform(post("/api/vehicles")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(vehicle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationNumber").value(vehicle.getRegistrationNumber()))
                .andExpect(jsonPath("$.ownerName").value(vehicle.getOwnerName()));

        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    public void testAddVehicle_RegistrationNumberExists() throws Exception {
        // Mocking repositori untuk simulasikan nomor registrasi yang sudah ada
        when(vehicleRepository.existsById(anyString())).thenReturn(true);

        // Mencoba menambahkan kendaraan dengan nomor registrasi yang sudah ada
        mockMvc.perform(post("/api/vehicles")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(vehicle)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Registration number already exists."));

        verify(vehicleRepository, times(0)).save(any(Vehicle.class));
    }

    @Test
    public void testGetAllVehicles() throws Exception {
        // Mocking repositori untuk mengembalikan daftar kendaraan
        when(vehicleService.getAllVehicles()).thenReturn(Arrays.asList(vehicle));

        // Mencoba mengambil semua kendaraan
        mockMvc.perform(get("/api/vehicles/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].registrationNumber").value(vehicle.getRegistrationNumber()))
                .andExpect(jsonPath("$[0].ownerName").value(vehicle.getOwnerName()));

        verify(vehicleService, times(1)).getAllVehicles();
    }

    @Test
    public void testGetVehicleByRegistrationNumber() throws Exception {
        // Mocking repositori untuk mencari kendaraan berdasarkan nomor registrasi
        when(vehicleService.getVehicleByRegistrationNumber(anyString())).thenReturn(vehicle);

        // Mencoba mengambil kendaraan berdasarkan nomor registrasi
        mockMvc.perform(get("/api/vehicles/{registrationNumber}", vehicle.getRegistrationNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationNumber").value(vehicle.getRegistrationNumber()))
                .andExpect(jsonPath("$.ownerName").value(vehicle.getOwnerName()));

        verify(vehicleService, times(1)).getVehicleByRegistrationNumber(anyString());
    }

    @Test
    public void testUpdateVehicle_Success() throws Exception {
        Vehicle updatedVehicle = new Vehicle("123ABC", "Jane Doe", "5678 Oak Avenue", "Honda", 2015, 1800, "Blue", "Diesel");

        // Mocking repositori untuk menemukan kendaraan yang ada dan memperbarui
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

        // Mencoba memperbarui kendaraan
        mockMvc.perform(put("/api/vehicles/{registrationNumber}", vehicle.getRegistrationNumber())
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updatedVehicle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerName").value("Jane Doe"))
                .andExpect(jsonPath("$.address").value("5678 Oak Avenue"));

        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    public void testDeleteVehicle() throws Exception {
        // Mocking repositori untuk menemukan kendaraan dan menghapusnya
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(vehicle));

        // Mencoba menghapus kendaraan
        mockMvc.perform(delete("/api/vehicles/{registrationNumber}", vehicle.getRegistrationNumber()))
                .andExpect(status().isNoContent());

        verify(vehicleRepository, times(1)).deleteById(anyString());
    }

}