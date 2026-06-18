@Penelitian
Feature: Pengajuan Penelitian - Validasi Form Langkah 1
  Saat mengajukan penelitian baru, tombol "Selanjutnya" hanya aktif
  jika Judul terisi dan Tahun berformat 4 digit.

  Background:
    Given saya sudah login sebagai dosen
    And saya membuka halaman tambah penelitian

  @SmokeTest
  Scenario: TC-PEN-01 - Halaman tambah penelitian termuat
    Then saya melihat judul "Ajukan Penelitian Baru"

  # Boundary Value Analysis pada field "Tahun" (tepat 4 digit) + Judul wajib (EP)
  @Validation @BVA @EP
  Scenario Outline: TC-PEN-02 - BVA Tahun & Judul menentukan tombol Selanjutnya (<status>)
    When saya isi Judul Penelitian "<judul>"
    And saya isi Tahun "<tahun>"
    Then tombol Selanjutnya "<status>"

    Examples:
      | judul                     | tahun | status   |
      | Riset AI untuk Pendidikan | 2026  | aktif    |
      | Riset AI untuk Pendidikan | 202   | nonaktif |
      | Riset AI untuk Pendidikan | 20266 | nonaktif |
      | (kosong)                  | 2026  | nonaktif |
