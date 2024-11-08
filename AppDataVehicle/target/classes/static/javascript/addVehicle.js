document.getElementById('vehicleForm').addEventListener('submit', function(event) {
    event.preventDefault();

    // Mengambil data kendaraan dari form
    const vehicleData = {
        registrationNumber: document.getElementById('registrationNumber').value,
        ownerName: document.getElementById('ownerName').value,
        address: document.getElementById('address').value,
        vehicleBrand: document.getElementById('vehicleBrand').value,
        yearOfManufacture: document.getElementById('yearOfManufacture').value,
        engineCapacity: document.getElementById('engineCapacity').value,
        vehicleColor: document.getElementById('vehicleColor').value,
        fuelType: document.getElementById('fuelType').value
    };

    // Mengirim data kendaraan ke server melalui API
    fetch('http://localhost:8080/api/vehicles', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(vehicleData)
    })
        .then(response => {
            if (!response.ok) {
                // Jika status code tidak OK (misal 400 atau 500)
                return response.json().then(errorData => {
                    // Menampilkan pesan error yang diterima dari backend
                    throw new Error(errorData.message || 'Terjadi Kesalahan pada Permintaan');
                });
            }
            return response.json();
        })
        .then(data => {
            // Menampilkan pesan sukses dan mengarahkan kembali ke halaman utama
            alert('Kendaraan berhasil ditambahkan!');
            window.location.href = 'index.html';
        })
        .catch(error => {
            // Menangani error dan menampilkan pesan kesalahan
            console.error('Error:', error);
            alert(error.message);  // Menampilkan pesan error yang diterima
        });
});
