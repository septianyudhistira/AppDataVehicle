document.addEventListener('DOMContentLoaded', () => {
    // Ambil referensi ke elemen form dan div untuk menampilkan hasil
    const vehicleForm = document.getElementById('vehicleForm');
    const resultDiv = document.getElementById('result');

    // Event listener untuk menangani pengiriman form
    vehicleForm.addEventListener('submit', function(event) {
        event.preventDefault();  // Mencegah form melakukan submit default (reload halaman)

        // Ambil nilai input dari form
        const registrationNumber = document.getElementById('registrationNumber').value;
        const ownerName = document.getElementById('ownerName').value;
        const address = document.getElementById('address').value;
        const vehicleBrand = document.getElementById('vehicleBrand').value;
        const yearOfManufacture = document.getElementById('yearOfManufacture').value;
        const engineCapacity = document.getElementById('engineCapacity').value;
        const vehicleColor = document.getElementById('vehicleColor').value;
        const fuelType = document.getElementById('fuelType').value;

        // Validasi Tahun Pembuatan (harus antara 1900 dan 2023)
        if (yearOfManufacture < 1900 || yearOfManufacture > 2023) {
            alert('Tahun Pembuatan harus antara 1900 dan 2023');
            return;  // Jika validasi gagal, hentikan eksekusi
        }

        // Buat objek data kendaraan
        const vehicleData = {
            registrationNumber,
            ownerName,
            address,
            vehicleBrand,
            yearOfManufacture,
            engineCapacity,
            vehicleColor,
            fuelType
        };

        // Kirim data kendaraan ke server menggunakan fetch API
        fetch('http://localhost:8080/api/vehicles', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'  // Menyatakan bahwa data dikirim dalam format JSON
            },
            body: JSON.stringify(vehicleData)  // Mengubah data kendaraan menjadi string JSON
        })
            .then(response => response.json())  // Mengambil respons dari server dan mengubahnya menjadi JSON
            .then(data => {
                // Tampilkan data kendaraan yang berhasil disimpan
                resultDiv.innerHTML = `
                    <h4>Data Kendaraan Tersimpan:</h4>
                    <ul>
                        <li><strong>Nomor Registrasi:</strong> ${data.registrationNumber}</li>
                        <li><strong>Nama Pemilik:</strong> ${data.ownerName}</li>
                        <li><strong>Alamat:</strong> ${data.address}</li>
                        <li><strong>Merk Kendaraan:</strong> ${data.vehicleBrand}</li>
                        <li><strong>Tahun Pembuatan:</strong> ${data.yearOfManufacture}</li>
                        <li><strong>Kapasitas Silinder:</strong> ${data.engineCapacity}</li>
                        <li><strong>Warna Kendaraan:</strong> ${data.vehicleColor}</li>
                        <li><strong>Bahan Bakar:</strong> ${data.fuelType}</li>
                    </ul>
                `;

                // Reset form setelah data berhasil disimpan
                vehicleForm.reset();
            })
            .catch(error => {
                // Tangani jika ada kesalahan saat menyimpan data
                console.error('Error:', error);
                alert('Terjadi kesalahan saat menyimpan data kendaraan.');
            });
    });
});
