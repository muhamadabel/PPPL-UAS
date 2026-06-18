# Test Suite — E2E Modul Dosen (PAK / Tridharma) SIA UGN

**Mata kuliah:** Praktikum Pengujian Perangkat Lunak
**Kelompok:** 1 (PAD 2)
**Aplikasi diuji (SUT):** SIA UGN — Sistem Informasi Akademik Universitas Global Nusantara
**Alur (user flow):** Login → Dashboard → Angka Kredit → Input BKD → Penelitian → Kegiatan Mengajar (6 halaman)
**Teknik desain test case:** Equivalence Partitioning (EP) & Boundary Value Analysis (BVA)

> Versi tabel siap-impor ke Google Sheets/Excel ada di [`test-cases.csv`](test-cases.csv).

---

## 1. Analisis Teknik Desain Test Case

### 1.1 Login — field Email & Password (EP)

| Field | Kelas Ekuivalen Valid | Kelas Ekuivalen Tidak Valid |
|-------|----------------------|------------------------------|
| Email | Email terdaftar & format benar (`dosen@gmail.com`) | Kosong; format salah (`bukan-email`); tidak terdaftar (`tidakada@gmail.com`) |
| Password | Password benar (`dosen123`) | Kosong; password salah (`passwordx`) |

Kombinasi yang diuji (1 wakil per kelas) menghasilkan TC-LOGIN-01..05.

### 1.2 Input BKD — field "Jumlah" (BVA)

Spesifikasi: jumlah kegiatan **minimal 1** (atribut `min="1"`, dan FE meng-clamp via `Math.max(1, …)`).
Nilai batas yang diuji: tepat di bawah batas (0, −5), tepat di batas (1), dan di atas batas (2, 10).

| Nilai input | Partisi | Nilai diharapkan tersimpan |
|-------------|---------|----------------------------|
| −5 | < batas (invalid) | 1 (ter-koreksi) |
| 0  | < batas (invalid) | 1 (ter-koreksi) |
| 1  | batas bawah (valid) | 1 |
| 2  | dalam range (valid) | 2 |
| 10 | dalam range (valid) | 10 |

### 1.3 Penelitian — field "Tahun" (BVA) + "Judul" (EP)

Spesifikasi: tombol **Selanjutnya** aktif hanya bila `Judul` terisi **dan** `Tahun` tepat **4 digit** (`/^\d{4}$/`).
Nilai batas panjang digit Tahun: 3 (kurang), 4 (pas), 5 (lebih).

| Judul | Tahun | Partisi | Tombol Selanjutnya |
|-------|-------|---------|--------------------|
| terisi | 2026 (4 digit) | valid | aktif |
| terisi | 202 (3 digit) | < batas | nonaktif |
| terisi | 20266 (5 digit) | > batas | nonaktif |
| kosong | 2026 | judul invalid | nonaktif |

---

## 2. Tabel Test Case

Kolom Status sudah diperbarui sesuai hasil eksekusi **2026-06-18** (semua `Pass`).

### Modul: Login (`/loginpage`)

| ID | Judul | Teknik | Precondition | Data Input | Langkah | Expected Result | Scenario (Gherkin) | Status |
|----|-------|--------|--------------|-----------|---------|-----------------|--------------------|--------|
| TC-LOGIN-01 | Login valid sebagai dosen | EP (valid) | BE & FE jalan, akun ter-seed | email=`dosen@gmail.com`, pass=`dosen123` | Isi email & password, klik Sign In | Diarahkan ke `/dashboard` | `login.feature` → "Login berhasil…" | Pass |
| TC-LOGIN-02 | Password salah | EP (invalid) | idem | email valid, pass=`passwordx` | Isi, klik Sign In | Pesan "Email atau password salah.", tetap di login | "Login gagal karena password salah" | Pass |
| TC-LOGIN-03 | Akun tidak terdaftar | EP (invalid) | idem | email=`tidakada@gmail.com` | Isi, klik Sign In | Pesan "Akun belum terdaftar." | "Login gagal karena akun belum terdaftar" | Pass |
| TC-LOGIN-04 | Email kosong | EP/BVA (invalid) | idem | email=kosong, pass=`dosen123` | Isi, klik Sign In | Submit diblok (HTML5 required), tetap di login | Outline EP/BVA `tetap_login` | Pass |
| TC-LOGIN-05 | Password kosong | EP/BVA (invalid) | idem | email valid, pass=kosong | Isi, klik Sign In | Submit diblok, tetap di login | Outline EP/BVA `tetap_login` | Pass |
| TC-LOGIN-06 | Format email tidak valid | EP (invalid) | idem | email=`bukan-email` | Isi, klik Sign In | Submit diblok (type=email), tetap di login | Outline EP/BVA `tetap_login` | Pass |

### Modul: Input BKD (`/dosen/angka-kredit/input-bkd`)

| ID | Judul | Teknik | Precondition | Data Input | Langkah | Expected Result | Scenario | Status |
|----|-------|--------|--------------|-----------|---------|-----------------|----------|--------|
| TC-BKD-01 | Halaman BKD termuat | Smoke | Login dosen, BE master-kegiatan tersedia | — | Buka halaman input BKD | Judul "Input BKD (Beban Kerja Dosen)" tampil | "Halaman input BKD termuat" | Pass |
| TC-BKD-02 | Jumlah = 0 → clamp 1 | BVA (< batas) | Login, pilih kegiatan | jumlah=0 | Isi Jumlah 0 | Nilai tersimpan = 1 | Outline BVA Jumlah | Pass |
| TC-BKD-03 | Jumlah = 1 (batas bawah) | BVA (batas) | idem | jumlah=1 | Isi Jumlah 1 | Nilai tersimpan = 1 | Outline BVA Jumlah | Pass |
| TC-BKD-04 | Jumlah = 2 (valid) | BVA (dalam range) | idem | jumlah=2 | Isi Jumlah 2 | Nilai tersimpan = 2 | Outline BVA Jumlah | Pass |
| TC-BKD-05 | Jumlah = −5 → clamp 1 | BVA (< batas) | idem | jumlah=−5 | Isi Jumlah −5 | Nilai tersimpan = 1 | Outline BVA Jumlah | Pass |
| TC-BKD-06 | Jumlah = 10 (valid) | BVA (dalam range) | idem | jumlah=10 | Isi Jumlah 10 | Nilai tersimpan = 10 | Outline BVA Jumlah | Pass |
| TC-BKD-07 | Submit BKD 1 kegiatan | Positive/E2E | Login, BE menerima POST | kegiatan #1, jumlah=2 | Pilih kegiatan, isi, lanjut, Submit BKD | Pesan "BKD Berhasil Disubmit!" | "Submit BKD … berhasil" | Pass |

### Modul: Penelitian (`/dosen/penelitian/tambah`)

| ID | Judul | Teknik | Precondition | Data Input | Langkah | Expected Result | Scenario | Status |
|----|-------|--------|--------------|-----------|---------|-----------------|----------|--------|
| TC-PEN-01 | Halaman tambah termuat | Smoke | Login dosen | — | Buka tambah penelitian | Judul "Ajukan Penelitian Baru" tampil | "Halaman tambah penelitian termuat" | Pass |
| TC-PEN-02 | Judul+Tahun 4 digit | BVA (batas valid) | idem | judul terisi, tahun=2026 | Isi judul & tahun | Tombol Selanjutnya **aktif** | Outline BVA Tahun | Pass |
| TC-PEN-03 | Tahun 3 digit | BVA (< batas) | idem | judul terisi, tahun=202 | Isi judul & tahun | Tombol Selanjutnya **nonaktif** | Outline BVA Tahun | Pass |
| TC-PEN-04 | Tahun 5 digit | BVA (> batas) | idem | judul terisi, tahun=20266 | Isi judul & tahun | Tombol Selanjutnya **nonaktif** | Outline BVA Tahun | Pass |
| TC-PEN-05 | Judul kosong | EP (invalid) | idem | judul kosong, tahun=2026 | Isi tahun saja | Tombol Selanjutnya **nonaktif** | Outline BVA Tahun | Pass |

### Modul: Kegiatan Mengajar (`/dosen/kegiatan-mengajar`)

| ID | Judul | Teknik | Precondition | Data Input | Langkah | Expected Result | Scenario | Status |
|----|-------|--------|--------------|-----------|---------|-----------------|----------|--------|
| TC-KM-01 | Halaman kegiatan mengajar termuat | Smoke | Login dosen | — | Buka halaman kegiatan mengajar | Judul "Dashboard Kegiatan Mengajar" tampil | "Halaman kegiatan mengajar termuat" | Pass |
| TC-KM-02 | Kolom pencarian menerima input | Functional | Login dosen | "Algoritma" | Ketik kata kunci di kolom pencarian | Kolom pencarian berisi "Algoritma" | "Kolom pencarian menerima kata kunci" | Pass |

### End-to-End (lintas 6 halaman)

| ID | Judul | Teknik | Precondition | Langkah | Expected Result | Scenario | Status |
|----|-------|--------|--------------|---------|-----------------|----------|--------|
| TC-E2E-01 | Telusuri modul PAK login→kegiatan mengajar | E2E happy path | BE & FE jalan, akun dosen ter-seed | Login → Dashboard → Angka Kredit → Input BKD → Penelitian → Kegiatan Mengajar | Tiap halaman tampil dengan judul yang benar | `e2e-dosen-pak.feature` | Pass |

---

## 3. Ringkasan Cakupan

- **Halaman teruji:** 6 (Login, Dashboard, Angka Kredit, Input BKD, Penelitian, Kegiatan Mengajar) — melebihi syarat minimal 5 halaman.
- **Total test case:** 21 (6 Login + 7 BKD + 5 Penelitian + 2 Kegiatan Mengajar + 1 E2E).
- **Teknik:** EP (Login email/password, Judul penelitian) + BVA (Jumlah BKD, Tahun penelitian).
- **Otomatisasi:** seluruh test case dipetakan ke skenario Gherkin/Cucumber dengan Page Object Model.
- **Hasil eksekusi (2026-06-18, `mvn clean verify`):** **23/23 skenario PASS** (0 gagal; termasuk Scenario Outline) lawan app live (FE `localhost:3001` + BE Laragon).
