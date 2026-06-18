package locators.dosen;

import org.openqa.selenium.By;

/** Locator Dashboard Angka Kredit (/dosen/angka-kredit) — sumber: src/app/dosen/angka-kredit/page.js */
public final class AngkaKreditLocators {

    public static final By HEADING = By.xpath("//h1[contains(normalize-space(.),'Dashboard Angka Kredit')]");
    public static final By INPUT_BKD_BUTTON = By.xpath("//button[contains(normalize-space(.),'Input BKD')]");
    public static final By RIWAYAT_BUTTON = By.xpath("//button[contains(normalize-space(.),'Riwayat BKD')]");

    private AngkaKreditLocators() {}
}
