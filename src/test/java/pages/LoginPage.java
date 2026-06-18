package pages;

import config.TestConfig;
import org.openqa.selenium.By;

import java.time.Duration;

/**
 * POM Halaman Login (/loginpage).
 * Sumber selector: src/components/ui/loginform.jsx (#email, #password, button[type=submit],
 * error box "Terjadi Kesalahan").
 */
public class LoginPage extends BasePage {

    private static final By EMAIL_INPUT    = By.cssSelector("input[type='email'], input[name='email'], #email");
    private static final By PASSWORD_INPUT = By.cssSelector("input[type='password'], input[name='password'], #password");
    private static final By LOGIN_BUTTON   = By.cssSelector("form button[type='submit']");
    private static final By FORM_CONTAINER = By.cssSelector("form");
    private static final By ERROR_TITLE    = By.xpath("//h3[contains(normalize-space(),'Terjadi Kesalahan')]");
    private static final By ERROR_MESSAGE  = By.xpath("//h3[contains(normalize-space(),'Terjadi Kesalahan')]/following-sibling::p");

    public LoginPage openLoginPage() {
        navigateToPath(TestConfig.PATH_LOGIN);
        waitForPresence(FORM_CONTAINER);
        return this;
    }

    public LoginPage enterEmail(String email) {
        typeIn(EMAIL_INPUT, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        typeIn(PASSWORD_INPUT, password);
        return this;
    }

    public LoginPage clickLoginButton() {
        jsClick(LOGIN_BUTTON);
        // tunggu sampai tombol tidak lagi "Signing in..."
        waitFor(d -> {
            try {
                return !d.findElement(LOGIN_BUTTON).getText().contains("Signing in");
            } catch (Exception e) {
                return true;
            }
        });
        return this;
    }

    public void login(String email, String password) {
        openLoginPage();
        if (email != null && !email.isEmpty()) enterEmail(email);
        if (password != null && !password.isEmpty()) enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginSuccessful() {
        try {
            waitFor(d -> d.getCurrentUrl().contains("/dashboard"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnLoginPage() {
        return getCurrentUrl().contains(TestConfig.PATH_LOGIN);
    }

    public boolean isLoginFormDisplayed() {
        return isDisplayed(FORM_CONTAINER) && isDisplayed(EMAIL_INPUT) && isDisplayed(PASSWORD_INPUT);
    }

    public boolean isErrorMessageDisplayed() {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(ERROR_TITLE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Apakah error box memuat pesan tertentu (mis. "Email atau password salah."). */
    public boolean hasErrorMessageContaining(String expected) {
        if (!isErrorMessageDisplayed()) return false;
        try {
            return getText(ERROR_MESSAGE).contains(expected);
        } catch (Exception e) {
            return isTextDisplayed(expected);
        }
    }
}
