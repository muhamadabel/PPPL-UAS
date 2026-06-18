package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.KegiatanMengajarPage;

/** Step khusus Dashboard Kegiatan Mengajar. */
public class KegiatanMengajarSteps {

    private final KegiatanMengajarPage kegiatanMengajarPage = new KegiatanMengajarPage();

    @Given("saya membuka halaman kegiatan mengajar")
    public void sayaMembukaHalamanKegiatanMengajar() {
        kegiatanMengajarPage.open();
        Assertions.assertTrue(kegiatanMengajarPage.isLoaded(), "Halaman kegiatan mengajar seharusnya termuat.");
    }

    @When("saya cari mata kuliah {string}")
    public void sayaCariMataKuliah(String query) {
        kegiatanMengajarPage.search(query);
    }

    @Then("kolom pencarian berisi {string}")
    public void kolomPencarianBerisi(String query) {
        Assertions.assertEquals(query, kegiatanMengajarPage.getSearchValue(),
                "Isi kolom pencarian tidak sesuai.");
    }
}
