package locators.dosen;

import org.openqa.selenium.By;

/** Locator Dashboard Kegiatan Mengajar (/dosen/kegiatan-mengajar) — sumber: src/app/dosen/kegiatan-mengajar/page.js */
public final class KegiatanMengajarLocators {

    public static final By HEADING = By.xpath("//h1[contains(normalize-space(.),'Dashboard Kegiatan Mengajar')]");
    public static final By SEARCH_INPUT = By.cssSelector("input[placeholder='Cari judul atau kode mata kuliah...']");

    private KegiatanMengajarLocators() {}
}
