@KegiatanMengajar
Feature: Dashboard Kegiatan Mengajar
  Dosen melihat daftar beban mengajar (mata kuliah yang diampu) beserta status & Angka Kredit-nya.

  Background:
    Given saya sudah login sebagai dosen
    And saya membuka halaman kegiatan mengajar

  @SmokeTest
  Scenario: TC-KM-01 - Halaman kegiatan mengajar termuat
    Then saya melihat judul "Dashboard Kegiatan Mengajar"

  @Functional
  Scenario: TC-KM-02 - Kolom pencarian menerima kata kunci mata kuliah
    When saya cari mata kuliah "Algoritma"
    Then kolom pencarian berisi "Algoritma"
