@Login
Feature: Login SIA UGN
  Sebagai dosen, saya ingin masuk ke sistem
  agar bisa mengakses modul Tridharma dan Angka Kredit (PAK).

  Background:
    Given saya membuka halaman login

  @SmokeTest @Positive
  Scenario: TC-LOGIN-01 - Login berhasil dengan kredensial dosen yang valid
    When saya login sebagai dosen dengan kredensial valid
    Then saya diarahkan ke halaman dashboard

  @NegativeTest
  Scenario: TC-LOGIN-02 - Login gagal karena password salah
    When saya login dengan email "dosen@gmail.com" dan password "passwordsalah"
    Then muncul pesan error "Email atau password salah."
    And saya tetap berada di halaman login

  # Catatan: untuk email tak terdaftar, BE membalas pesan generik yang sama dengan
  # password salah ("Email atau password salah.") — praktik umum keamanan (lihat BUG-REPORT, observasi).
  @NegativeTest
  Scenario: TC-LOGIN-03 - Login gagal karena akun belum terdaftar
    When saya login dengan email "tidakada@gmail.com" dan password "apapun123"
    Then muncul pesan error "Email atau password salah."
    And saya tetap berada di halaman login

  # Equivalence Partitioning & Boundary Value Analysis untuk form login
  @Validation @EP @BVA
  Scenario Outline: TC-LOGIN-04 - Validasi input login menghasilkan <hasil>
    When saya isi email "<email>" dan password "<password>"
    And saya klik tombol Sign In
    Then hasilnya "<hasil>"

    Examples:
      | email           | password  | hasil            |
      | dosen@gmail.com | dosen123  | dashboard        |
      | dosen@gmail.com | passwordx | error_kredensial |
      | (kosong)        | dosen123  | tetap_login      |
      | dosen@gmail.com | (kosong)  | tetap_login      |
      | bukan-email     | dosen123  | tetap_login      |
