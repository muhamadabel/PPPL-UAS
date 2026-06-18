package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.InputBkdPage;

/** Step khusus form Input BKD + BVA field Jumlah. */
public class BkdSteps {

    private final InputBkdPage inputBkdPage = new InputBkdPage();

    @Given("saya membuka halaman input BKD")
    public void sayaMembukaHalamanInputBkd() {
        inputBkdPage.open();
        Assertions.assertTrue(inputBkdPage.isLoaded(), "Halaman Input BKD seharusnya termuat.");
    }

    @When("saya pilih jenis kegiatan pertama")
    public void sayaPilihJenisKegiatanPertama() {
        inputBkdPage.pilihJenisKegiatanPertama();
    }

    @When("saya isi Jumlah dengan {string}")
    public void sayaIsiJumlahDengan(String nilai) {
        inputBkdPage.setJumlah(nilai);
    }

    @Then("nilai Jumlah yang tersimpan adalah {string}")
    public void nilaiJumlahTersimpan(String diharapkan) {
        Assertions.assertEquals(diharapkan, inputBkdPage.getJumlah(),
                "Nilai Jumlah tersimpan tidak sesuai (BVA clamping).");
    }

    @When("saya lanjut ke step terakhir")
    public void sayaLanjutKeStepTerakhir() {
        inputBkdPage.gotoLastStep();
    }

    @When("saya klik Submit BKD")
    public void sayaKlikSubmitBkd() {
        inputBkdPage.clickSubmit();
    }
}
