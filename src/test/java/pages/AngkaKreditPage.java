package pages;

import config.TestConfig;
import locators.dosen.AngkaKreditLocators;

/** POM Dashboard Angka Kredit (/dosen/angka-kredit). */
public class AngkaKreditPage extends BasePage {

    public void open() {
        navigateToPath(TestConfig.PATH_ANGKA_KREDIT);
    }

    public boolean isLoaded() {
        return isHeadingDisplayed("Dashboard Angka Kredit");
    }

    public void clickInputBkd() {
        // jsClick: tombol pakai router.push pada onClick; klik koordinat bisa ter-intercept navbar fixed.
        jsClick(AngkaKreditLocators.INPUT_BKD_BUTTON);
        waitForUrlContains("/input-bkd");
    }
}
