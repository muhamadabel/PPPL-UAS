package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.PenelitianTambahPage;

/** Step khusus form "Ajukan Penelitian Baru" + BVA field Tahun. */
public class PenelitianSteps {

    private final PenelitianTambahPage penelitianTambahPage = new PenelitianTambahPage();

    private static String norm(String v) {
        return "(kosong)".equals(v) ? "" : v;
    }

    @Given("saya membuka halaman tambah penelitian")
    public void sayaMembukaHalamanTambahPenelitian() {
        penelitianTambahPage.open();
        Assertions.assertTrue(penelitianTambahPage.isLoaded(), "Halaman tambah penelitian seharusnya termuat.");
    }

    @When("saya isi Judul Penelitian {string}")
    public void sayaIsiJudulPenelitian(String judul) {
        penelitianTambahPage.fillJudul(norm(judul));
    }

    @When("saya isi Tahun {string}")
    public void sayaIsiTahun(String tahun) {
        penelitianTambahPage.fillTahun(norm(tahun));
    }

    @Then("tombol Selanjutnya {string}")
    public void tombolSelanjutnya(String status) {
        boolean enabled = penelitianTambahPage.isNextEnabled();
        if ("aktif".equals(status)) {
            Assertions.assertTrue(enabled, "Tombol \"Selanjutnya\" seharusnya AKTIF.");
        } else if ("nonaktif".equals(status)) {
            Assertions.assertFalse(enabled, "Tombol \"Selanjutnya\" seharusnya NONAKTIF.");
        } else {
            throw new IllegalArgumentException("Status tidak dikenal: " + status);
        }
    }
}
