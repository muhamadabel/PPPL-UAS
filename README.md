# PPL - Pengujian Sistem Informasi Akademik (SIA-UGN)

> Repositori ini merupakan bagian dari tugas akhir mata kuliah **Praktikum Pengujian Perangkat Lunak** yang berfokus pada pengujian fungsional Sistem Informasi Akademik (SIA-UGN) modul **Dosen (Tridharma & Penilaian Angka Kredit / PAK)** menggunakan framework **Cucumber + Selenium** dengan pola desain **Page Object Model (POM)**.

---

## System Under Test (SUT)

**Sistem Informasi Akademik (SIA-UGN) — Modul Dosen** adalah aplikasi berbasis web untuk mengelola kegiatan Tridharma dosen dan perhitungan Angka Kredit. Sistem ini mencakup autentikasi dosen, pencatatan Beban Kerja Dosen (BKD), pengajuan penelitian, serta rekap kegiatan mengajar.

Halaman yang menjadi cakupan pengujian (alur end-to-end, 6 halaman):

| Halaman           | Deskripsi                                                       |
| ----------------- | -------------------------------------------------------------- |
| Login             | Autentikasi dosen ke dalam sistem                              |
| Dashboard         | Halaman utama setelah login (jadwal harian & pengumuman)       |
| Angka Kredit      | Dashboard angka kredit (PAK) dan navigasi ke Input BKD         |
| Input BKD         | Input beban kerja dosen / kegiatan Tridharma per kategori      |
| Penelitian        | Form pengajuan penelitian baru                                 |
| Kegiatan Mengajar | Daftar mata kuliah yang diampu beserta status & angka kreditnya |

---

## Test Suite

Test suite dibangun menggunakan **Cucumber (BDD)** dan **Selenium WebDriver** dengan bahasa Java. Skenario ditulis dalam format **Gherkin** (`.feature`) sehingga memisahkan perilaku bisnis dari implementasi teknis. Desain test case memakai teknik **Equivalence Partitioning (EP)** dan **Boundary Value Analysis (BVA)**.

### Framework & Tools

| Komponen           | Teknologi                         |
| ------------------ | --------------------------------- |
| Bahasa Pemrograman | Java 17                           |
| BDD Framework      | Cucumber                          |
| UI Automation      | Selenium WebDriver                |
| Test Runner        | JUnit 5 (via `CucumberRunner.java`) |
| Pola Desain        | Page Object Model (POM)           |
| Build Tool         | Maven                             |

### Feature Files (Skenario Pengujian)

| File                        | Modul yang Diuji                           | Teknik     |
| --------------------------- | ------------------------------------------ | ---------- |
| `login.feature`             | Login dosen                                | EP + BVA   |
| `input-bkd.feature`         | Input BKD (field Jumlah)                   | BVA        |
| `penelitian.feature`        | Pengajuan penelitian (field Tahun & Judul) | BVA + EP   |
| `kegiatan-mengajar.feature` | Dashboard kegiatan mengajar                | Smoke      |
| `e2e-dosen-pak.feature`     | Alur end-to-end lintas 6 halaman           | E2E        |

---

## Pembagian Tugas Kelompok

| Nama                  | Bagian                              | File Utama                                                                                       |
| --------------------- | ----------------------------------- | ------------------------------------------------------------------------------------------------ |
| **muhamadabel**       | Modul **Angka Kredit & Input BKD** (+ setup project) | `input-bkd.feature`, `InputBkdPage.java`, `AngkaKreditPage.java`, `BkdSteps.java` + `pom.xml`, `CucumberRunner.java`, `CucumberHooks.java`, `TestConfig.java`, `BasePage.java` |
| **NaelSucksAtCoding** | Modul **Login & Dashboard**                          | `login.feature`, `LoginPage.java`, `LoginSteps.java`, `CommonSteps.java`, `DashboardPage.java`, `NavbarComponent.java` |
| **exenthiast**        | Modul **Penelitian**                                 | `penelitian.feature`, `PenelitianPage.java`, `PenelitianTambahPage.java`, `PenelitianSteps.java` + `TEST-SUITE.md` (EP/BVA) |
| **dannysetiawan06**   | Modul **Kegiatan Mengajar** (+ E2E & dokumentasi)    | `kegiatan-mengajar.feature`, `KegiatanMengajarPage.java`, `KegiatanMengajarSteps.java` + `e2e-dosen-pak.feature`, `BUG-REPORT.md`, `presentasi.html` |

---

## Struktur Repositori

```
sia-ugn-testing/
│
├── data/
│   └── data_requirements.md                 # Kebutuhan data uji (akun, prasyarat BE)
│
├── docs/
│   ├── BUG-REPORT.md                         # Laporan bug yang ditemukan
│   └── presentasi.html                       # Halaman dokumentasi/presentasi
│
├── test-suite/
│   ├── TEST-SUITE.md                         # Analisis EP/BVA + tabel test case
│   └── test-cases.csv                        # Test case (siap impor ke spreadsheet)
│
├── src/
│   ├── main/java/org/example/Main.java
│   │
│   └── test/
│       ├── java/
│       │   ├── config/
│       │   │   └── TestConfig.java           # Konfigurasi global (base URL, akun, path)
│       │   ├── hooks/
│       │   │   └── CucumberHooks.java         # Setup/teardown browser + screenshot saat gagal
│       │   ├── locators/dosen/                # Kumpulan locator elemen UI tiap halaman
│       │   ├── pages/                         # Page Object Model (BasePage + halaman dosen)
│       │   ├── runner/
│       │   │   └── CucumberRunner.java        # Entry point eksekusi test suite
│       │   └── steps/                         # Step definitions (implementasi Gherkin)
│       │
│       └── resources/
│           ├── features/                      # File skenario Gherkin (.feature)
│           │   ├── login.feature
│           │   ├── input-bkd.feature
│           │   ├── penelitian.feature
│           │   ├── kegiatan-mengajar.feature
│           │   └── e2e-dosen-pak.feature
│           └── cucumber.properties            # Konfigurasi Cucumber
│
├── pom.xml
├── .gitignore
└── README.md
```

---

## Arsitektur dan Pola (Pattern)

### 1. Behavior-Driven Development (BDD) dengan Cucumber
- **Feature**: ditulis dalam sintaks Gherkin (Given, When, Then) agar mudah dibaca pihak non-teknis.
- **Steps**: setiap baris Gherkin punya method padanan di Java yang terikat lewat anotasi Cucumber (`@Given`, `@When`, `@Then`).

### 2. Page Object Model (POM)
- **BasePage**: menyimpan utilitas Selenium (explicit wait, klik, ketik, dll.) dan mengambil instance driver statis dari `CucumberHooks`.
- **Pages & Locators**: tiap halaman dosen direpresentasikan satu kelas; lokator UI dipisah di `locators/dosen` agar step definition tetap bersih.

---

## Manajemen WebDriver

WebDriver diinisialisasi statis menggunakan ChromeDriver lewat hooks Cucumber (`CucumberHooks.java`):
- `@Before`: WebDriverManager mengambil binary ChromeDriver otomatis, menyusun argumen Chrome, lalu membuka `TestConfig.BASE_URL`.
- `@After`: menutup browser (`driver.quit()`); bila skenario gagal, screenshot otomatis ditangkap & disimpan ke laporan dan folder `target/screenshots`.

---

## Dependensi Utama (`pom.xml`)

| Dependensi                                         | Versi  |
| -------------------------------------------------- | ------ |
| `selenium-java`                                    | 4.21.0 |
| `webdrivermanager`                                 | 5.8.0  |
| `cucumber-java` & `cucumber-junit-platform-engine` | 7.18.0 |
| `junit-jupiter`                                    | 5.10.2 |
| `junit-platform-suite`                             | 1.10.2 |
| `maven-cucumber-reporting` (laporan HTML)          | 5.8.1  |

---

## Cara Menjalankan Pengujian

Aplikasi diuji secara lokal, jadi backend & frontend harus berjalan dulu.

1. **Clone** repositori:
   ```bash
   git clone https://github.com/USERNAME/NAMA-REPO.git
   cd NAMA-REPO
   ```

2. Pastikan **Java JDK 17+**, **Maven**, dan **Google Chrome** sudah terpasang.

3. Jalankan backend (Laravel via Laragon) dan frontend Next.js:
   ```bash
   # frontend
   cd Fe-SIA-UGN-kelompok1
   npm run dev -- -p 3001
   ```
   Akun uji: `dosen@gmail.com` / `dosen123`.

4. Jalankan seluruh test suite (sesuaikan `BASE_URL` bila port FE berbeda):
   ```bash
   mvn clean verify
   ```
   Laporan HTML otomatis dibuat di `target/cucumber-html-reports/`.

5. Menjalankan modul tertentu saja (lewat tag Cucumber):
   ```bash
   mvn test -Dcucumber.filter.tags="@Login"
   ```

---

## Konvensi Git

- Setiap anggota mengerjakan bagiannya pada branch masing-masing dengan format:
  ```
  feature/<nama>-<modul>
  ```
  Contoh: `feature/abel-setup`, `feature/nael-login`, `feature/alfiz-bkd`, `feature/danny-e2e`
- Setelah selesai, buat **Pull Request** ke branch `main` untuk direview sebelum di-merge.
