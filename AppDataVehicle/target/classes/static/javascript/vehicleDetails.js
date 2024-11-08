const apiUrl = 'http://localhost:8080/api/vehicles';

// Fungsi untuk mengambil detail kendaraan berdasarkan nomor registrasi
function getVehicleDetails(registrationNumber) {
    fetch(`${apiUrl}/${registrationNumber}`)  // Mengambil data kendaraan berdasarkan nomor registrasi
        .then(response => response.json())  // Mengonversi response menjadi format JSON
        .then(data => {
            // Menampilkan detail kendaraan pada halaman
            document.getElementById('detailsContent').innerHTML = `
                <div class="form-row">
                    <p><strong>Nomor Registrasi:</strong> ${data.registrationNumber}</p>
                    <p><strong>Nama Pemilik:</strong> ${data.ownerName}</p>
                    <p><strong>Alamat:</strong> ${data.address}</p>
                    <p><strong>Brand Kendaraan:</strong> ${data.vehicleBrand}</p>
                    <p><strong>Tahun Pembuatan:</strong> ${data.yearOfManufacture}</p>
                    <p><strong>Kapasitas Mesin:</strong> ${data.engineCapacity} cc</p>
                    <p><strong>Warna Kendaraan:</strong> ${data.vehicleColor}</p>
                    <p><strong>Tipe Bahan Bakar:</strong> ${data.fuelType}</p>
                </div>
            `;
        })
        .catch(error => {
            console.error('Error fetching vehicle details:', error);
            alert('Gagal memuat data kendaraan.');
        });
}

// Ambil nomor registrasi dari query parameter URL
const urlParams = new URLSearchParams(window.location.search);
const registrationNumber = urlParams.get('registrationNumber');

// Jika nomor registrasi ditemukan, ambil detail kendaraan
if (registrationNumber) {
    getVehicleDetails(registrationNumber);
}
