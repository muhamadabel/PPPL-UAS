package pages;

import config.TestConfig;
import locators.dosen.InputBkdLocators;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * POM form wizard Input BKD (/dosen/angka-kredit/input-bkd).
 * Catatan: field Jumlah meng-clamp nilai < 1 menjadi 1 (Math.max(1, parseInt||1)).
 */
public class InputBkdPage extends BasePage {

    public void open() {
        navigateToPath(TestConfig.PATH_INPUT_BKD);
    }

    public boolean isLoaded() {
        return isHeadingDisplayed("Input BKD");
    }

    /** Pilih opsi pertama (index 1; index 0 = placeholder "-- Pilih jenis kegiatan --"). */
    public void pilihJenisKegiatanPertama() {
        WebElement el = waitForVisibility(InputBkdLocators.JENIS_KEGIATAN_SELECT);
        new Select(el).selectByIndex(1);
    }

    /** Isi field Jumlah (React controlled) lalu beri waktu React melakukan clamping. */
    public void setJumlah(String value) {
        setReactInput(InputBkdLocators.JUMLAH_INPUT, value);
        sleep(350);
    }

    public String getJumlah() {
        return getValue(InputBkdLocators.JUMLAH_INPUT);
    }

    public void clickNext() {
        // jsClick: memicu onClick langsung, hindari klik ter-intercept sticky bar / navbar fixed.
        jsClick(InputBkdLocators.NEXT_BUTTON);
        sleep(400);
    }

    /** Klik "Selanjutnya" sampai tombol "Submit BKD" muncul (step terakhir). */
    public void gotoLastStep() {
        for (int i = 0; i < 6; i++) {
            if (isDisplayed(InputBkdLocators.SUBMIT_BUTTON)) {
                return; // sudah di step terakhir
            }
            if (isDisplayed(InputBkdLocators.NEXT_BUTTON)) {
                clickNext();
            } else {
                sleep(400); // beri waktu render
            }
        }
    }

    public void clickSubmit() {
        jsClick(InputBkdLocators.SUBMIT_BUTTON);
    }

    public boolean isSubmitSuccess() {
        return isTextDisplayed("BKD Berhasil Disubmit!");
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
