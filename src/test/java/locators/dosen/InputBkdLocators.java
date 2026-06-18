package locators.dosen;

import org.openqa.selenium.By;

/**
 * Locator form wizard Input BKD (/dosen/angka-kredit/input-bkd)
 * Sumber: src/app/dosen/angka-kredit/input-bkd/page.js
 */
public final class InputBkdLocators {

    public static final By JENIS_KEGIATAN_SELECT = By.cssSelector("select");
    public static final By JUMLAH_INPUT = By.cssSelector("input[type='number']");
    public static final By NEXT_BUTTON = By.xpath("//button[normalize-space()='Selanjutnya']");
    public static final By PREV_BUTTON = By.xpath("//button[normalize-space()='Sebelumnya']");
    public static final By SUBMIT_BUTTON = By.xpath("//button[normalize-space()='Submit BKD']");
    public static final By TAMBAH_KEGIATAN_BUTTON = By.xpath("//button[contains(normalize-space(.),'Tambah Kegiatan')]");

    private InputBkdLocators() {}
}
