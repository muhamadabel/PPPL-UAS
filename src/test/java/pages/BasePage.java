package pages;

import config.TestConfig;
import hooks.CucumberHooks;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage — kelas dasar semua Page Object (POM).
 * Menyediakan akses driver + helper interaksi & sinkronisasi (explicit wait).
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = CucumberHooks.driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.EXPLICIT_WAIT_SECONDS));
    }

    // ---- Navigasi ----
    public void navigateToPath(String path) {
        driver.get(TestConfig.BASE_URL + path);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // ---- Wait ----
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void waitForUrlContains(String urlSubstring) {
        wait.until(ExpectedConditions.urlContains(urlSubstring));
    }

    public <T> T waitFor(ExpectedCondition<T> condition) {
        return wait.until(condition);
    }

    // ---- Aksi ----
    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    protected void jsClick(By locator) {
        WebElement el = waitForPresence(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    protected void typeIn(By locator, String text) {
        WebElement el = waitForClickable(locator);
        el.clear();
        el.sendKeys(text);
    }

    /**
     * Set nilai pada input/textarea React (controlled component) secara andal:
     * memakai native value setter + dispatch event 'input'/'change' agar onChange React terpicu.
     * (clear()+sendKeys sering tidak ter-register oleh React controlled input.)
     */
    protected void setReactInput(By locator, String value) {
        WebElement el = waitForVisibility(locator);
        ((JavascriptExecutor) driver).executeScript(
                "const el = arguments[0], v = arguments[1];" +
                "const proto = el.tagName === 'TEXTAREA' ? window.HTMLTextAreaElement.prototype : window.HTMLInputElement.prototype;" +
                "const setter = Object.getOwnPropertyDescriptor(proto, 'value').set;" +
                "setter.call(el, v);" +
                "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "el.dispatchEvent(new Event('change', { bubbles: true }));",
                el, value);
    }

    /** Tunggu elemen visible dalam N detik; return false bila timeout (tanpa melempar). */
    protected boolean waitVisibleWithin(By locator, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void scrollIntoView(By locator) {
        WebElement el = waitForPresence(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    // ---- Query ----
    protected String getText(By locator) {
        return waitForVisibility(locator).getText().trim();
    }

    protected String getValue(By locator) {
        return waitForVisibility(locator).getAttribute("value");
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected boolean isEnabled(By locator) {
        try {
            return waitForVisibility(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /** Apakah ada heading (h1/h2/h3) yang mengandung teks tertentu. */
    public boolean isHeadingDisplayed(String text) {
        By heading = By.xpath(
                "//h1[contains(normalize-space(.),'" + text + "')]" +
                " | //h2[contains(normalize-space(.),'" + text + "')]" +
                " | //h3[contains(normalize-space(.),'" + text + "')]"
        );
        try {
            waitForVisibility(heading);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Apakah ada teks apa pun yang tampil di halaman (untuk pesan sukses, dsb). */
    public boolean isTextDisplayed(String text) {
        By byText = By.xpath("//*[contains(normalize-space(.),'" + text + "')][not(self::script)][not(self::style)]");
        try {
            waitForVisibility(byText);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
