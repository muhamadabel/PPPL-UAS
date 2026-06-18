package pages;

import config.TestConfig;

/** POM Dashboard (/dashboard) — halaman landing setelah login. */
public class DashboardPage extends BasePage {

    public boolean isLoaded() {
        try {
            waitForUrlContains(TestConfig.PATH_DASHBOARD);
        } catch (Exception ignored) {}
        return getCurrentUrl().contains(TestConfig.PATH_DASHBOARD);
    }
}
