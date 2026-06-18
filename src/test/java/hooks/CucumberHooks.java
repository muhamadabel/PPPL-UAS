package hooks;

import config.TestConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lifecycle hooks Cucumber: buka Chrome sebelum tiap skenario,
 * screenshot otomatis saat GAGAL (di-attach ke report + disimpan), tutup browser.
 */
public class CucumberHooks {

    private static final DateTimeFormatter TIMESTAMP_FMT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    public static WebDriver driver;

    private static int passedCount = 0;
    private static int failedCount = 0;

    @io.cucumber.java.BeforeAll
    public static void setupBeforeAll() {
        System.out.println("\n========================================");
        System.out.println("   GLOBAL SETUP — E2E Modul Dosen (PAK)");
        System.out.println("   Target: " + TestConfig.BASE_URL);
        System.out.println("========================================\n");
        // Redam log bising Selenium/WebDriverManager
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
    }

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        System.out.println("\n========================================");
        System.out.println("▶  START: " + scenario.getName());
        System.out.println("   Tags  : " + scenario.getSourceTagNames());
        System.out.println("========================================");

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (TestConfig.HEADLESS) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--log-level=3");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestConfig.IMPLICIT_WAIT_SECONDS));

        driver.get(TestConfig.BASE_URL);
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            failedCount++;
            System.out.println("✖  FAILED: " + scenario.getName());
            takeScreenshot(scenario);
        } else {
            passedCount++;
            System.out.println("✔  PASSED: " + scenario.getName());
        }
        System.out.println("========================================\n");

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @io.cucumber.java.AfterAll
    public static void printSummary() {
        System.out.println("\n========================================");
        System.out.println("   RINGKASAN EKSEKUSI");
        System.out.println("   ✔ PASSED : " + passedCount);
        System.out.println("   ✖ FAILED : " + failedCount);
        System.out.println("   TOTAL    : " + (passedCount + failedCount));
        System.out.println("========================================\n");
    }

    private void takeScreenshot(Scenario scenario) {
        try {
            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(bytes, "image/png", "Screenshot on Failure");

            String timestamp = LocalDateTime.now().format(TIMESTAMP_FMT);
            String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9_\\-]", "_");
            safeName = safeName.substring(0, Math.min(safeName.length(), 50));

            Path dir = Paths.get(TestConfig.SCREENSHOT_DIR);
            Files.createDirectories(dir);
            Path filePath = dir.resolve(timestamp + "_" + safeName + ".png");
            Files.write(filePath, bytes);
            System.out.println("   📸 Screenshot: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("   ⚠ Gagal simpan screenshot: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ⚠ Gagal ambil screenshot: " + e.getMessage());
        }
    }
}
