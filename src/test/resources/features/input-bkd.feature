@BKD
Feature: Input BKD (Beban Kerja Dosen)
  Dosen menginput kegiatan Tridharma untuk dihitung Angka Kredit-nya,
  lalu mengajukannya ke manajer untuk divalidasi.

  Background:
    Given saya sudah login sebagai dosen
    And saya membuka halaman input BKD

  @SmokeTest
  Scenario: TC-BKD-01 - Halaman input BKD termuat dengan benar
    Then saya melihat judul "Input BKD"

  # Boundary Value Analysis pada field "Jumlah" (minimal 1; FE meng-clamp Math.max(1,...))
  @Validation @BVA
  Scenario Outline: TC-BKD-02 - BVA field Jumlah (input <input> menjadi <tersimpan>)
    When saya pilih jenis kegiatan pertama
    And saya isi Jumlah dengan "<input>"
    Then nilai Jumlah yang tersimpan adalah "<tersimpan>"

    Examples:
      | input | tersimpan |
      | 0     | 1         |
      | 1     | 1         |
      | 2     | 2         |
      | -5    | 1         |
      | 10    | 10        |

  @Positive @E2E
  Scenario: TC-BKD-07 - Submit BKD satu kegiatan berhasil
    When saya pilih jenis kegiatan pertama
    And saya isi Jumlah dengan "2"
    And saya lanjut ke step terakhir
    And saya klik Submit BKD
    Then saya melihat pesan "BKD Berhasil Disubmit!"
