package pages;

import config.TestConfig;
import locators.dosen.NavbarLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Komponen Navbar bersama: navigasi antar modul dosen lewat dropdown "Tridharma" (Radix).
 * Dropdown Radix kadang tak terbuka oleh synthetic click; dicoba beberapa strategi,
 * dengan fallback navigasi via URL agar alur E2E tetap berjalan.
 */
public class NavbarComponent extends BasePage {

    /** Coba buka dropdown via beberapa strategi. return true bila menu terbuka. */
    private boolean tryOpenTridharma() {
        try {
            WebElement trigger = waitForClickable(NavbarLocators.TRIDHARMA_TRIGGER);
            scrollIntoView(NavbarLocators.TRIDHARMA_TRIGGER);
            trigger.click();
            if (waitVisibleWithin(NavbarLocators.MENU_CONTENT, 2)) return true;

            jsClick(NavbarLocators.TRIDHARMA_TRIGGER);
            if (waitVisibleWithin(NavbarLocators.MENU_CONTENT, 2)) return true;

            trigger.sendKeys(Keys.RETURN); // Radix membuka pada Enter/Space
            if (waitVisibleWithin(NavbarLocators.MENU_CONTENT, 2)) return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /** label: "Angka Kredit" | "Penelitian" | "Publikasi Ilmiah" | "Kegiatan Mengajar" | "Gaji" */
    public void goToTridharmaMenu(String label) {
        if (tryOpenTridharma()) {
            By item = NavbarLocators.menuItem(label);
            if (waitVisibleWithin(item, 3)) {
                jsClick(item);
                return;
            }
        }
        // Fallback: navigasi langsung via URL bila dropdown tak dapat dibuka oleh automation.
        String path = pathFor(label);
        if (path != null) navigateToPath(path);
    }

    private String pathFor(String label) {
        switch (label) {
            case "Angka Kredit":     return TestConfig.PATH_ANGKA_KREDIT;
            case "Penelitian":       return TestConfig.PATH_PENELITIAN;
            case "Kegiatan Mengajar":return "/dosen/kegiatan-mengajar";
            case "Gaji":             return "/administrasi/payroll";
            default:                 return null;
        }
    }
}
