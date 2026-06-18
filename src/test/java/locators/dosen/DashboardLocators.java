package locators.dosen;

import org.openqa.selenium.By;

/** Locator Dashboard (/dashboard) — sumber: src/app/dashboard/page.js */
public final class DashboardLocators {

    public static final By JADWAL_SECTION = By.xpath("//*[contains(normalize-space(.),'Jadwal Harian')]");

    private DashboardLocators() {}
}
