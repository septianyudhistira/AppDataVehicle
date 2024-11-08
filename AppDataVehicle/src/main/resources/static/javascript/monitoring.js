const apiUrl = 'http://localhost:8080/api/vehicles';

// Muat semua kendaraan saat halaman pertama kali dimuat
document.addEventListener('DOMContentLoaded', function() {
    fetch(`${apiUrl}/all`)
        .then(response => response.json())
        .then(data => {
            displayVehicles(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

// Event listener untuk pencarian kendaraan
document.getElementById('searchButton').addEventListener('click', function() {
    const registrationNumber = document.getElementById('registrationNumber').value.trim();
    const ownerName = document.getElementById('ownerName').value.trim();

    // Menyusun URL pencarian berdasarkan input
    let searchUrl = `${apiUrl}/search`; // Ganti dengan '/search'
    if (registrationNumber) {
        searchUrl += `?registrationNumber=${registrationNumber}`;
    }
    if (ownerName) {
        // Menambahkan parameter ownerName jika ada
        searchUrl += searchUrl.includes('?') ? `&ownerName=${ownerName}` : `?ownerName=${ownerName}`;
    }

    // Mengambil data kendaraan berdasarkan kriteria pencarian
    fetch(searchUrl)
        .then(response => response.json())
        .then(data => {
            displayVehicles(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

// Fungsi untuk menampilkan data kendaraan pada tabel
function displayVehicles(vehicles) {
    const tableBody = document.getElementById('vehicleTableBody');
    tableBody.innerHTML = ''; // Clear tabel sebelum mengisi

    if (vehicles.length === 0) {
        // Jika tidak ada kendaraan, tampilkan pesan 'Data tidak ditemukan'
        tableBody.innerHTML = '<tr><td colspan="8">Data tidak ditemukan</td></tr>';
        return;
    }

    // Menambahkan data kendaraan ke tabel
    vehicles.forEach((vehicle, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="no">${index + 1}</td>
            <td class="no-reg">${vehicle.registrationNumber}</td>
            <td>${vehicle.ownerName}</td>
            <td class="brand">${vehicle.vehicleBrand}</td>
            <td class="year">${vehicle.yearOfManufacture}</td>
            <td>${vehicle.engineCapacity} cc</td>
            <td>${vehicle.vehicleColor}</td>
            <td class="fuel">${vehicle.fuelType}</td>
            <td class="action-align">
                <button class="action-btn detail-btn" onclick="viewDetails('${vehicle.registrationNumber}')">Detail</button>
                <button class="action-btn edit-btn" onclick="editVehicle('${vehicle.registrationNumber}')">Edit</button>
                <button class="action-btn delete-btn" onclick="deleteVehicle('${vehicle.registrationNumber}')">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

// Fungsi untuk melihat detail kendaraan
function viewDetails(registrationNumber) {
    window.location.href = `details.html?registrationNumber=${registrationNumber}`;
}

// Fungsi untuk mengedit kendaraan
function editVehicle(registrationNumber) {
    window.location.href = `edit.html?registrationNumber=${registrationNumber}`;
}

// Variabel untuk menyimpan kendaraan yang akan dihapus
let vehicleToDelete = null;

// Fungsi untuk menghapus kendaraan
function deleteVehicle(registrationNumber) {
    vehicleToDelete = registrationNumber; // Simpan nomor registrasi kendaraan yang akan dihapus

    // Ubah teks konfirmasi di modal
    const deleteMessage = document.getElementById('deleteMessage');
    deleteMessage.innerHTML = `Anda yakin menghapus data dengan nomor registrasi <strong>${registrationNumber}</strong> ini?`;

    // Tampilkan modal konfirmasi
    document.getElementById('deleteModal').style.display = 'block';
}

// Fungsi untuk menutup modal konfirmasi
function closeModal() {
    document.getElementById('deleteModal').style.display = 'none'; // Sembunyikan modal
}

// Fungsi untuk mengonfirmasi penghapusan kendaraan
document.getElementById('confirmDeleteBtn').addEventListener('click', function() {
    if (vehicleToDelete) {
        // Kirim permintaan DELETE ke server untuk menghapus kendaraan
        fetch(`${apiUrl}/${vehicleToDelete}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('Kendaraan berhasil dihapus!');
                window.location.reload(); // Reload halaman untuk menampilkan data terbaru
            })
            .catch(error => {
                console.error('Error:', error);
            });

        closeModal(); // Setelah menghapus, tutup modal
    }
});
