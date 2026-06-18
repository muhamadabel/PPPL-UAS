package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.DashboardPage;
import pages.LoginPage;

/** Step khusus Login + EP/BVA. */
public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final DashboardPage dashboardPage = new DashboardPage();

    // "(kosong)" pada Examples = string kosong.
    private static String norm(String v) {
        return "(kosong)".equals(v) ? "" : v;
    }

    @When("saya login dengan email {string} dan password {string}")
    public void sayaLoginDenganEmailDanPassword(String email, String password) {
        String e = norm(email);
        String p = norm(password);
        if (!e.isEmpty()) loginPage.enterEmail(e);
        if (!p.isEmpty()) loginPage.enterPassword(p);
        loginPage.clickLoginButton();
    }

    @When("saya isi email {string} dan password {string}")
    public void sayaIsiEmailDanPassword(String email, String password) {
        String e = norm(email);
        String p = norm(password);
        if (!e.isEmpty()) loginPage.enterEmail(e);
        if (!p.isEmpty()) loginPage.enterPassword(p);
    }

    @When("saya klik tombol Sign In")
    public void sayaKlikTombolSignIn() {
        loginPage.clickLoginButton();
    }

    @Then("muncul pesan error {string}")
    public void munculPesanError(String pesan) {
        Assertions.assertTrue(loginPage.hasErrorMessageContaining(pesan),
                "Pesan error \"" + pesan + "\" tidak muncul.");
    }

    @Then("saya tetap berada di halaman login")
    public void sayaTetapDiHalamanLogin() {
        sleep(800);
        Assertions.assertTrue(loginPage.isOnLoginPage(), "Seharusnya tetap di halaman login.");
    }

    // Outcome gabungan untuk Scenario Outline EP/BVA.
    @Then("hasilnya {string}")
    public void hasilnya(String expected) {
        switch (expected) {
            case "dashboard":
                Assertions.assertTrue(dashboardPage.isLoaded(), "Diharapkan diarahkan ke /dashboard.");
                break;
            case "error_kredensial":
                Assertions.assertTrue(loginPage.hasErrorMessageContaining("Email atau password salah."),
                        "Diharapkan pesan error kredensial.");
                break;
            case "tetap_login":
                sleep(800);
                Assertions.assertTrue(loginPage.isOnLoginPage(), "Diharapkan tetap di halaman login.");
                break;
            default:
                throw new IllegalArgumentException("Hasil tidak dikenal: " + expected);
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
