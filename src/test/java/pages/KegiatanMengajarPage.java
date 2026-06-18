package pages;

import config.TestConfig;
import locators.dosen.KegiatanMengajarLocators;

/** POM Dashboard Kegiatan Mengajar (/dosen/kegiatan-mengajar). */
public class KegiatanMengajarPage extends BasePage {

    public void open() {
        navigateToPath(TestConfig.PATH_KEGIATAN_MENGAJAR);
    }

    public boolean isLoaded() {
        return isHeadingDisplayed("Dashboard Kegiatan Mengajar");
    }

    public void search(String query) {
        setReactInput(KegiatanMengajarLocators.SEARCH_INPUT, query);
    }

    public String getSearchValue() {
        return getValue(KegiatanMengajarLocators.SEARCH_INPUT);
    }
}
