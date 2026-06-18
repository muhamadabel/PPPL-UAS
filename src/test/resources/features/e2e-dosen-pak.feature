@E2E
Feature: Alur End-to-End Modul Dosen (PAK / Tridharma)
  Satu alur lengkap (6 halaman) yang ditelusuri seorang dosen:
  Login -> Dashboard -> Angka Kredit -> Input BKD -> Penelitian -> Kegiatan Mengajar.

  @SmokeTest
  Scenario: TC-E2E-01 - Dosen menelusuri modul PAK dari login sampai kegiatan mengajar
    # Halaman 1: Login
    Given saya membuka halaman login
    When saya login sebagai dosen dengan kredensial valid
    # Halaman 2: Dashboard
    Then saya diarahkan ke halaman dashboard

    # Halaman 3: Angka Kredit
    When saya buka menu Tridharma "Angka Kredit"
    Then saya melihat judul "Dashboard Angka Kredit"

    # Halaman 4: Input BKD
    When saya klik tombol Input BKD
    Then saya melihat judul "Input BKD"

    # Halaman 5: Penelitian
    When saya buka menu Tridharma "Penelitian"
    Then saya melihat judul "Dashboard Penelitian"

    # Halaman 6: Kegiatan Mengajar
    When saya buka menu Tridharma "Kegiatan Mengajar"
    Then saya melihat judul "Dashboard Kegiatan Mengajar"
