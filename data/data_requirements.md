# Kebutuhan Data Uji — Modul Dosen (PAK / Tridharma)

Data yang harus tersedia di backend (hasil seed) agar test berjalan.

## Akun

| Peran | Email | Password | Catatan |
|-------|-------|----------|---------|
| Dosen | dosen@gmail.com | dosen123 | Akun utama yang dipakai seluruh skenario dosen |

> Override via `.env` (`DOSEN_EMAIL`, `DOSEN_PASSWORD`) bila akun berbeda.

## Prasyarat data backend

- **Master kegiatan BKD** (`GET /lecturer/bkd/master-kegiatan`) mengembalikan ≥1 kategori & opsi.
  Dibutuhkan oleh skenario TC-BKD-02..07 (memilih jenis kegiatan & submit).
- **Master jabatan** (`GET /lecturer/bkd/master-jabatan`) tersedia (dipakai halaman Angka Kredit).
- Akun dosen memiliki `id_user_si` valid agar submit BKD diterima backend.

## Data input per test (ringkas)

| Field | Nilai uji | Sumber |
|-------|-----------|--------|
| Email login | dosen@gmail.com / passwordx / kosong / bukan-email | login.feature |
| Jumlah BKD | -5, 0, 1, 2, 10 | input-bkd.feature (BVA) |
| Tahun penelitian | 202, 2026, 20266 | penelitian.feature (BVA) |
| Judul penelitian | "Riset AI untuk Pendidikan" / kosong | penelitian.feature (EP) |

Detail lengkap test case lihat [`../test-suite/TEST-SUITE.md`](../test-suite/TEST-SUITE.md).
