# Bug Report — E2E Modul Dosen (PAK / Tridharma) SIA UGN

**Kelompok:** 1 (PAD 2) · **Aplikasi:** SIA UGN · **Tanggal uji:** 2026-06-18
**Lingkungan uji:** Windows 11 · Chrome + Selenium WebDriver 4 (Java/Maven · Cucumber-JVM) · FE Next.js (`localhost:3001`) · BE Laravel (Laragon)
**Hasil eksekusi:** 21/21 skenario **PASS** (`mvn clean verify`).

> Severity: Critical / Major / Minor / Trivial. Priority: High / Medium / Low.
> Status: Open / In Progress / Fixed / Won't Fix.
> Catatan kolom **Sumber temuan**: `Analisis kode` = ditemukan saat mendesain test & membaca kode; `Runtime` = ditemukan saat menjalankan suite. Konfirmasi runtime saat eksekusi.

---

## BUG-01 — Field "Jumlah" pada Input BKD mengoreksi input tidak valid secara diam-diam

| | |
|---|---|
| **ID** | BUG-01 |
| **Modul / Halaman** | Input BKD — `/dosen/angka-kredit/input-bkd` |
| **Severity** | Minor |
| **Priority** | Low |
| **Sumber temuan** | Analisis kode — **DIKONFIRMASI runtime** (TC-BKD-02: input `0` & `-5` → nilai tersimpan `1`) |
| **Status** | Open |

**Deskripsi:** Input `Jumlah` (`<input type="number" min="1">`) memakai `onChange={... Math.max(1, parseInt(e.target.value) || 1)}`. Akibatnya nilai `0`, negatif, atau kosong **otomatis diubah menjadi 1 tanpa pesan apa pun**, dan field tidak bisa dikosongkan untuk diketik ulang.

**Langkah Reproduksi:**
1. Login sebagai dosen → buka Input BKD.
2. Pilih sebuah jenis kegiatan.
3. Pada field Jumlah, ketik `0` (atau `-5`, atau hapus isinya).

**Hasil Diharapkan:** Muncul pesan validasi (mis. "Jumlah minimal 1") **atau** field membiarkan user mengetik lalu memvalidasi saat blur/submit.
**Hasil Aktual:** Nilai langsung berubah menjadi `1` tanpa notifikasi; user bisa salah paham nilai yang ter-input.

**Bukti:** `src/app/dosen/angka-kredit/input-bkd/page.js` baris ~375. Screenshot kegagalan otomatis ter-attach di report bila assertion berbeda.

---

## BUG-02 — Validasi "Tahun" hanya mengecek 4 digit, bukan rentang tahun yang masuk akal

| | |
|---|---|
| **ID** | BUG-02 |
| **Modul / Halaman** | Penelitian — `/dosen/penelitian/tambah` |
| **Severity** | Minor |
| **Priority** | Low |
| **Sumber temuan** | Analisis kode (terkait TC-PEN-02..04) |
| **Status** | Open |

**Deskripsi:** Validasi tahun memakai `/^\d{4}$/` (`isStep1Valid`). Tahun seperti `0000` atau `9999` lolos sebagai valid karena tetap 4 digit, padahal bukan tahun yang masuk akal.

**Langkah Reproduksi:**
1. Login dosen → Tambah Penelitian.
2. Isi Judul, isi Tahun = `0000`.

**Hasil Diharapkan:** Tahun dibatasi rentang wajar (mis. 1950–tahun berjalan), atau tombol Selanjutnya tetap nonaktif untuk tahun tidak masuk akal.
**Hasil Aktual:** Tombol "Selanjutnya" menjadi **aktif** (dianggap valid).

**Bukti:** `src/app/dosen/penelitian/tambah/page.js` baris 116 (`isStep1Valid`).

---

## BUG-03 — Redirect setelah login mengasumsikan role tunggal (akun multi-role tidak ter-redirect)

| | |
|---|---|
| **ID** | BUG-03 |
| **Modul / Halaman** | Login — `/loginpage` (`src/components/ui/loginform.jsx`) |
| **Severity** | Major |
| **Priority** | Medium |
| **Sumber temuan** | Analisis kode — **perlu konfirmasi runtime** dengan akun multi-role |
| **Status** | Open |

**Deskripsi:** Setelah login sukses, `roles = response.data.user.roles` (array). Redirect memakai perbandingan longgar `roles == 'dosen'` dll. Untuk akun **satu** role array ter-coerce ke string (`['dosen'] == 'dosen'` → true) sehingga lolos. Untuk akun **multi-role** (`['dosen','asesor']` → `"dosen,asesor"`) semua perbandingan gagal → **tidak ada redirect**, user tertahan di layar login meski login berhasil.

**Langkah Reproduksi (perlu akun multi-role):**
1. Login dengan akun yang punya >1 role.
2. Amati: token tersimpan, tetapi halaman tidak berpindah ke `/dashboard` maupun `/adminpage`.

**Hasil Diharapkan:** Redirect berdasarkan apakah array `roles` *mengandung* role tertentu (`roles.includes('dosen')`).
**Hasil Aktual:** Perbandingan `==` terhadap string gagal untuk array >1 elemen → tidak redirect.

**Bukti:** `src/components/ui/loginform.jsx` baris 33–38 & 88–92.

---

## OBS-01 — Email tak terdaftar memunculkan pesan generik (observasi, bukan defect)

| | |
|---|---|
| **ID** | OBS-01 |
| **Modul / Halaman** | Login — `/loginpage` |
| **Severity** | Info |
| **Sumber temuan** | Runtime (TC-LOGIN-03) |
| **Status** | Won't Fix (by design) |

**Deskripsi:** Saat awal mendesain TC-LOGIN-03, diharapkan pesan "Akun belum terdaftar." untuk email yang tidak ada. Namun BE membalas `HTTP 401 {"message":"Email atau password salah."}` — sama persis dengan kasus password salah. Artinya sistem **tidak membedakan** email tak terdaftar vs password salah.

**Catatan:** Ini umumnya **disengaja** (mencegah *user enumeration* — penyerang tak bisa menebak email mana yang terdaftar). Jadi diklasifikasikan sebagai observasi, bukan bug. Test case (TC-LOGIN-03) sudah disesuaikan untuk mengharapkan pesan generik tersebut.

---

## Template Bug Baru (temuan runtime)

```
## BUG-0X — <judul singkat>
- Modul / Halaman :
- Severity / Priority :
- Sumber temuan : Runtime (TC-...)
- Langkah Reproduksi :
  1.
- Hasil Diharapkan :
- Hasil Aktual :
- Bukti : (screenshot di reports/, baris kode, URL)
- Status : Open
```

---

### Ringkasan

| ID | Judul | Severity | Status |
|----|-------|----------|--------|
| BUG-01 | Jumlah BKD ter-koreksi diam-diam | Minor | Open (dikonfirmasi TC-BKD-02) |
| BUG-02 | Validasi Tahun hanya 4 digit | Minor | Open |
| BUG-03 | Redirect login gagal untuk akun multi-role | Major | Open (perlu akun multi-role) |
| OBS-01 | Pesan login generik utk email tak terdaftar | Info | Won't Fix (by design) |
