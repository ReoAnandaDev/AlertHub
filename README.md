# AlertHub  

**Sebuah aplikasi mobile berbasis Android yang dirancang untuk memberikan pemberitahuan darurat saat ponsel terjatuh.**  

---

## **Deskripsi**  
**AlertHub** adalah aplikasi mobile berbasis Java untuk Android yang menggunakan sensor perangkat untuk mendeteksi kondisi jatuhnya ponsel. Ketika terdeteksi, aplikasi secara otomatis mengirimkan **pemberitahuan darurat** kepada kontak yang telah ditentukan melalui **SMS** dan **panggilan telepon**. Pesan darurat ini mencakup **titik koordinat lokasi jatuh**, membantu kontak darurat mengetahui lokasi kejadian secara akurat.  

---

## **Fitur Utama**  

1. **Deteksi Jatuh Otomatis:**  
   - Menggunakan sensor akselerometer dan giroskop untuk mendeteksi kejadian jatuh.  
   - Dirancang untuk memberikan respon cepat terhadap kondisi darurat.  

2. **Pengiriman Pemberitahuan Darurat:**  
   - **SMS Darurat:** Mengirimkan pesan teks otomatis berisi lokasi jatuh (dengan koordinat GPS).  
   - **Panggilan Telepon:** Secara otomatis menghubungi kontak darurat untuk memastikan bantuan dapat diberikan segera.  

3. **Manajemen Kontak Darurat:**  
   - Pengguna dapat menambahkan, mengedit, atau menghapus kontak darurat sesuai kebutuhan.  
   - Mendukung hingga beberapa kontak darurat.  

4. **Integrasi Lokasi:**  
   - Lokasi pengguna diambil secara otomatis menggunakan layanan **GPS**.  
   - Titik koordinat lokasi disertakan dalam SMS untuk membantu pelacakan.  

5. **Desain Sederhana dan Intuitif:**  
   - Tampilan ramah pengguna dengan antarmuka yang mudah digunakan.  
   - Berfungsi dengan lancar pada perangkat Android berbagai versi.  

---

## **Keunggulan**  
- Respon otomatis terhadap kondisi darurat tanpa memerlukan tindakan manual.  
- Memberikan rasa aman bagi pengguna, terutama dalam situasi rawan atau ketika jauh dari orang lain.  
- Memanfaatkan layanan SMS dan panggilan telepon yang andal untuk pemberitahuan darurat.  

---

## **Teknologi yang Digunakan**  

1. **Bahasa Pemrograman:**  
   - **Java**: Bahasa utama untuk membangun aplikasi Android.  

2. **Framework dan API:**  
   - **Android Sensor API:** Untuk mendeteksi perubahan gerakan dan posisi ponsel.  
   - **Location Services (GPS):** Untuk mengambil titik koordinat lokasi secara akurat.  
   - **Telephony API:** Untuk mengirim SMS dan melakukan panggilan darurat.  

3. **Kompatibilitas:**  
   - Aplikasi kompatibel dengan berbagai versi Android, mulai dari Android 6.0 (Marshmallow) ke atas.  

---

## **Cara Kerja Singkat**  

1. **Set Kontak Darurat:**  
   - Pengguna memasukkan kontak darurat ke dalam aplikasi.  

2. **Deteksi Jatuh:**  
   - Sensor pada ponsel mendeteksi gerakan jatuh berdasarkan pola akselerasi.  

3. **Pengiriman Notifikasi:**  
   - Ketika terdeteksi, aplikasi secara otomatis mengirimkan SMS darurat dan memulai panggilan telepon ke kontak darurat yang telah disimpan.  

4. **Penyertaan Lokasi:**  
   - Lokasi jatuh disertakan dalam pesan SMS untuk mempermudah identifikasi lokasi.  

---

**AlertHub** memberikan solusi inovatif untuk meningkatkan keselamatan dan memberikan respon cepat dalam situasi darurat, khususnya bagi pengguna yang beraktivitas di area berisiko tinggi.  
