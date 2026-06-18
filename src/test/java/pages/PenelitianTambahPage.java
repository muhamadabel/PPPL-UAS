package pages;

import config.TestConfig;
import locators.dosen.PenelitianTambahLocators;

/**
 * POM form "Ajukan Penelitian Baru" (/dosen/penelitian/tambah) — Langkah 1.
 * Target BVA: tombol "Selanjutnya" aktif hanya bila Judul terisi & Tahun 4 digit.
 */
public class PenelitianTambahPage extends BasePage {

    public void open() {
        navigateToPath(TestConfig.PATH_PENELITIAN_TAMBAH);
    }

    public boolean isLoaded() {
        return isHeadingDisplayed("Ajukan Penelitian Baru");
    }

    public void fillJudul(String judul) {
        setReactInput(PenelitianTambahLocators.JUDUL_INPUT, judul);
    }

    public void fillTahun(String tahun) {
        setReactInput(PenelitianTambahLocators.TAHUN_INPUT, tahun);
    }

    public boolean isNextEnabled() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        return isEnabled(PenelitianTambahLocators.NEXT_BUTTON);
    }
}
