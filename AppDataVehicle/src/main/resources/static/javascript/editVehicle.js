const apiUrl = 'http://localhost:8080/api/vehicles';

// Fungsi untuk mengambil data kendaraan berdasarkan nomor registrasi
function getVehicleData(registrationNumber) {
    fetch(`${apiUrl}/${registrationNumber}`)
        .then(response => response.json())
        .then(data => {
            // Mengisi form dengan data kendaraan yang diterima dari API
            document.getElementById('editRegistrationNumber').value = data.registrationNumber;
            document.getElementById('editOwnerName').value = data.ownerName;
            document.getElementById('editAddress').value = data.address;
            document.getElementById('editVehicleBrand').value = data.vehicleBrand;
            document.getElementById('editYearOfManufacture').value = data.yearOfManufacture;
            document.getElementById('editEngineCapacity').value = data.engineCapacity;
            document.getElementById('editVehicleColor').value = data.vehicleColor;
            document.getElementById('editFuelType').value = data.fuelType;
        })
        .catch(error => {
            console.error('Error fetching vehicle data:', error);
        });
}

// Event listener untuk form edit kendaraan
document.getElementById('editVehicleForm').addEventListener('submit', function(event) {
    event.preventDefault();

    // Mengambil data kendaraan dari form
    const vehicleData = {
        registrationNumber: document.getElementById('editRegistrationNumber').value,
        ownerName: document.getElementById('editOwnerName').value,
        address: document.getElementById('editAddress').value,
        vehicleBrand: document.getElementById('editVehicleBrand').value,
        yearOfManufacture: document.getElementById('editYearOfManufacture').value,
        engineCapacity: document.getElementById('editEngineCapacity').value,
        vehicleColor: document.getElementById('editVehicleColor').value,
        fuelType: document.getElementById('editFuelType').value
    };

    // Mengirim data kendaraan yang sudah diperbarui ke server
    fetch(`${apiUrl}/${vehicleData.registrationNumber}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(vehicleData)
    })
        .then(response => response.json())
        .then(data => {
            // Menampilkan pesan sukses dan mengarahkan ke halaman utama
            alert('Kendaraan berhasil diperbarui!');
            window.location.href = 'index.html'; // Redirect ke halaman monitoring
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Terjadi kesalahan saat memperbarui kendaraan.');
        });
});

// Mendapatkan parameter registrationNumber dari URL query string
const urlParams = new URLSearchParams(window.location.search);
const registrationNumber = urlParams.get('registrationNumber');

// Jika registrationNumber ada, ambil data kendaraan untuk ditampilkan
if (registrationNumber) {
    getVehicleData(registrationNumber);
}
