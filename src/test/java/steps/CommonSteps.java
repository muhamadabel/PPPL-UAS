package steps;

import config.TestConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.*;

/** Step umum: login, navigasi antar halaman, assert judul & pesan. */
public class CommonSteps {

    private final LoginPage loginPage = new LoginPage();
    private final DashboardPage dashboardPage = new DashboardPage();
    private final NavbarComponent navbar = new NavbarComponent();
    private final AngkaKreditPage angkaKreditPage = new AngkaKreditPage();
    private final CommonPage commonPage = new CommonPage();

    @Given("saya membuka halaman login")
    public void sayaMembukaHalamanLogin() {
        loginPage.openLoginPage();
        Assertions.assertTrue(loginPage.isLoginFormDisplayed(), "Form login seharusnya tampil.");
    }

    @Given("saya sudah login sebagai dosen")
    public void sayaSudahLoginSebagaiDosen() {
        loginPage.login(TestConfig.DOSEN_EMAIL, TestConfig.DOSEN_PASSWORD);
        Assertions.assertTrue(loginPage.isLoginSuccessful(), "Login dosen seharusnya berhasil (redirect ke /dashboard).");
    }

    @When("saya login sebagai dosen dengan kredensial valid")
    public void sayaLoginSebagaiDosenValid() {
        loginPage.enterEmail(TestConfig.DOSEN_EMAIL);
        loginPage.enterPassword(TestConfig.DOSEN_PASSWORD);
        loginPage.clickLoginButton();
    }

    @Then("saya diarahkan ke halaman dashboard")
    public void sayaDiarahkanKeDashboard() {
        Assertions.assertTrue(dashboardPage.isLoaded(), "Seharusnya berada di /dashboard.");
    }

    @When("saya buka menu Tridharma {string}")
    public void sayaBukaMenuTridharma(String label) {
        navbar.goToTridharmaMenu(label);
    }

    @When("saya klik tombol Input BKD")
    public void sayaKlikTombolInputBkd() {
        angkaKreditPage.clickInputBkd();
    }

    @Then("saya melihat judul {string}")
    public void sayaMelihatJudul(String judul) {
        Assertions.assertTrue(commonPage.isHeadingDisplayed(judul), "Judul \"" + judul + "\" tidak terlihat.");
    }

    @Then("saya melihat pesan {string}")
    public void sayaMelihatPesan(String pesan) {
        Assertions.assertTrue(commonPage.isTextDisplayed(pesan), "Pesan \"" + pesan + "\" tidak muncul.");
    }
}
