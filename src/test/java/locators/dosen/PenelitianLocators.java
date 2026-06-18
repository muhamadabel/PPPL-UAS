package locators.dosen;

import org.openqa.selenium.By;

/** Locator Dashboard Penelitian (/dosen/penelitian) — sumber: src/app/dosen/penelitian/page.js */
public final class PenelitianLocators {

    public static final By HEADING = By.xpath("//h1[contains(normalize-space(.),'Dashboard Penelitian')]");
    public static final By TAMBAH_BUTTON = By.xpath("//button[contains(normalize-space(.),'Tambah Penelitian')]");
    public static final By SEARCH_INPUT = By.cssSelector("input[placeholder*='Cari judul atau bidang']");

    private PenelitianLocators() {}
}
