<div align="center">

<img src="https://img.shields.io/badge/SIA--UGN-E2E%20TESTING-015023?style=for-the-badge&labelColor=DABC4E" alt="SIA-UGN E2E Testing" />

# Pengujian End-to-End — Modul Dosen SIA-UGN

**Tridharma &amp; Penilaian Angka Kredit (PAK)**

_Automated UI Testing · BDD (Gherkin) · Page Object Model_

<br/>

![Java](https://img.shields.io/badge/Java-17-015023?style=for-the-badge&logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4.21-015023?style=for-the-badge&logo=selenium&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-7.18-015023?style=for-the-badge&logo=cucumber&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit-5-015023?style=for-the-badge&logo=junit5&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-015023?style=for-the-badge&logo=apachemaven&logoColor=white)

![Tests](https://img.shields.io/badge/Tests-23%2F23%20PASSING-1a7f37?style=for-the-badge)
![POM](https://img.shields.io/badge/Pattern-Page%20Object%20Model-DABC4E?style=for-the-badge&labelColor=015023)
![BDD](https://img.shields.io/badge/Approach-BDD%20Gherkin-DABC4E?style=for-the-badge&labelColor=015023)

</div>

---

> [!NOTE]
> **Tugas Akhir Praktikum Pengujian Perangkat Lunak — Kelompok 1 (PAD 2).**
> Menguji satu alur lengkap modul **Dosen** pada Sistem Informasi Akademik Universitas Global Nusantara (SIA-UGN): autentikasi, beban kerja dosen (BKD), angka kredit, penelitian, dan kegiatan mengajar.

## 🧭 Alur Pengujian — 6 Halaman

```
Login  →  Dashboard  →  Angka Kredit  →  Input BKD  →  Penelitian  →  Kegiatan Mengajar
```

| # | Halaman | Yang Diuji | Teknik |
|:-:|---------|------------|:------:|
| 1 | **Login** | Kredensial valid · password salah · akun tak terdaftar · field kosong · format email | `EP` · `BVA` |
| 2 | **Dashboard** | Redirect otomatis setelah login berhasil | `Smoke` |
| 3 | **Angka Kredit** | Halaman termuat + navigasi ke Input BKD | `Smoke` |
| 4 | **Input BKD** | Validasi field Jumlah (−5, 0, 1, 2, 10 → dikoreksi min. 1) + submit BKD | `BVA` |
| 5 | **Penelitian** | Validasi Tahun (3 / 4 / 5 digit) + Judul wajib | `BVA` · `EP` |
| 6 | **Kegiatan Mengajar** | Halaman termuat + pencarian mata kuliah | `Smoke` |

## 👥 Tim &amp; Pembagian Modul

| Anggota | Modul yang Dikerjakan |
|---------|-----------------------|
| 🧩 **muhamadabel** | Angka Kredit &amp; Input BKD · setup project |
| 🔐 **NaelSucksAtCoding** | Login &amp; Dashboard |
| 🔬 **exenthiast** | Penelitian · test suite (EP/BVA) |
| 📚 **dannysetiawan06** | Kegiatan Mengajar · alur End-to-End · dokumentasi |

## 🧪 Teknik Desain Test Case

<table>
<tr>
<td width="50%" valign="top">

### Equivalence Partitioning (EP)
Membagi input menjadi kelompok yang diperlakukan sama, lalu menguji **satu wakil** per kelompok — mis. email **valid** / **salah** / **kosong**.

</td>
<td width="50%" valign="top">

### Boundary Value Analysis (BVA)
Menguji nilai tepat di **batas** (tempat bug paling sering muncul) — mis. Tahun **3 / 4 / 5** digit, atau Jumlah **0 / 1 / 2**.

</td>
</tr>
</table>

## 🛠️ Teknologi &amp; Dependensi

| Komponen | Teknologi | Versi |
|----------|-----------|:-----:|
| Bahasa Pemrograman | Java | 17 |
| UI Automation | Selenium WebDriver | 4.21.0 |
| BDD Framework | Cucumber-JVM | 7.18.0 |
| Test Runner | JUnit 5 Platform Suite | 5.10.2 |
| Driver Manager | WebDriverManager | 5.8.0 |
| Build &amp; Report | Maven · maven-cucumber-reporting | 5.8.1 |

## 📁 Struktur Proyek

<details>
<summary><b>📂 Klik untuk melihat struktur folder</b></summary>

<br/>

```
sia-ugn-testing/
├── data/                            # Kebutuhan data uji
├── docs/                            # Bug report + halaman presentasi
├── test-suite/                      # Test case (EP/BVA) — MD + CSV
├── src/
│   ├── main/java/org/example/       # Main (placeholder)
│   └── test/
│       ├── java/
│       │   ├── config/              # TestConfig (base URL, akun, path)
│       │   ├── hooks/               # CucumberHooks (driver + screenshot on fail)
│       │   ├── locators/dosen/      # Selector elemen UI tiap halaman
│       │   ├── pages/               # Page Object Model
│       │   ├── runner/              # CucumberRunner (JUnit Platform Suite)
│       │   └── steps/               # Step definitions (glue Gherkin → Selenium)
│       └── resources/
│           ├── features/            # Skenario Gherkin (.feature)
│           └── cucumber.properties
├── pom.xml
└── README.md
```

</details>

## ▶️ Cara Menjalankan

> [!IMPORTANT]
> Backend (Laravel via **Laragon**) &amp; Frontend (`npm run dev -- -p 3001`) harus berjalan dulu.
> Akun uji: `dosen@gmail.com` / `dosen123`.

```bash
git clone https://github.com/muhamadabel/PPPL-UAS.git
cd PPPL-UAS
mvn clean verify        # jalankan semua test + generate report HTML
```

> [!TIP]
> Jalankan modul tertentu saja → `mvn test -Dcucumber.filter.tags="@Login"`
> Mau lihat browser bergerak saat demo → set `HEADLESS=false` lebih dulu.

## 📊 Hasil &amp; Laporan

- ✅ **23 / 23 skenario PASS** (`mvn clean verify`)
- 📈 Report HTML otomatis → `target/cucumber-html-reports/`
- 🐞 Bug report → [`docs/BUG-REPORT.md`](docs/BUG-REPORT.md)
- 📋 Test suite EP/BVA → [`test-suite/TEST-SUITE.md`](test-suite/TEST-SUITE.md)
- 🖥️ Dokumentasi / presentasi → [`docs/presentasi.html`](docs/presentasi.html)

## 🔀 Konvensi Git

Tiap anggota bekerja di branch `feature/<nama>-<modul>`, lalu **Pull Request** ke `main` untuk direview sebelum di-merge.

<div align="center">
<br/>

![UGN](https://img.shields.io/badge/Universitas%20Global%20Nusantara-Praktikum%20Pengujian%20PL-015023?style=flat-square&labelColor=DABC4E)

<sub>© 2026 · Kelompok 1 PAD 2</sub>

</div>
