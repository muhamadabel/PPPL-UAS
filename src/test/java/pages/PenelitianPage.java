package pages;

import config.TestConfig;
import locators.dosen.PenelitianLocators;

/** POM Dashboard Penelitian (/dosen/penelitian). */
public class PenelitianPage extends BasePage {

    public void open() {
        navigateToPath(TestConfig.PATH_PENELITIAN);
    }

    public boolean isLoaded() {
        return isHeadingDisplayed("Dashboard Penelitian");
    }

    public void clickTambah() {
        click(PenelitianLocators.TAMBAH_BUTTON);
        waitForUrlContains("/penelitian/tambah");
    }
}
