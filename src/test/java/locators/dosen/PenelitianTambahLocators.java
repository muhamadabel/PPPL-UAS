package locators.dosen;

import org.openqa.selenium.By;

/**
 * Locator form "Ajukan Penelitian Baru" (/dosen/penelitian/tambah) — Langkah 1 "Data Penelitian".
 * Sumber: src/app/dosen/penelitian/tambah/page.js
 * Tombol "Selanjutnya" memiliki atribut disabled saat isStep1Valid=false
 * (judul kosong ATAU tahun bukan 4 digit) -> dipakai untuk BVA.
 */
public final class PenelitianTambahLocators {

    public static final By JUDUL_INPUT = By.cssSelector("input[placeholder*='Implementasi Deep Learning']");
    public static final By TAHUN_INPUT = By.cssSelector("input[placeholder='2026']");
    public static final By NEXT_BUTTON = By.xpath("//button[normalize-space()='Selanjutnya']");

    private PenelitianTambahLocators() {}
}
