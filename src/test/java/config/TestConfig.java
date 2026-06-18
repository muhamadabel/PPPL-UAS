package config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Konfigurasi terpusat. Nilai dibaca dari .env (opsional) atau environment variable,
 * dengan fallback ke default untuk dev lokal modul dosen SIA UGN.
 */
public class TestConfig {

    private static final Dotenv ENV = loadEnv();

    // Target aplikasi yang diuji. Default = hosted (tanpa perlu install BE/FE).
    // Override ke lokal: set env BASE_URL=http://localhost:3001
    public static final String BASE_URL = get("BASE_URL", "https://sia.trisuladana.com");

    // Akun dosen hasil seed BE.
    public static final String DOSEN_EMAIL = get("DOSEN_EMAIL", "dosen@gmail.com");
    public static final String DOSEN_PASSWORD = get("DOSEN_PASSWORD", "dosen123");

    // Browser
    public static final String BROWSER = get("BROWSER", "chrome");
    public static final boolean HEADLESS = Boolean.parseBoolean(get("HEADLESS", "false"));

    // Timeouts (detik)
    public static final int IMPLICIT_WAIT_SECONDS = 0;
    public static final int EXPLICIT_WAIT_SECONDS = 15;
    public static final int PAGE_LOAD_TIMEOUT_SECONDS = 30;

    public static final String SCREENSHOT_DIR = "target/screenshots";

    // Path halaman (alur modul dosen / PAK)
    public static final String PATH_LOGIN = "/loginpage";
    public static final String PATH_DASHBOARD = "/dashboard";
    public static final String PATH_ANGKA_KREDIT = "/dosen/angka-kredit";
    public static final String PATH_INPUT_BKD = "/dosen/angka-kredit/input-bkd";
    public static final String PATH_PENELITIAN = "/dosen/penelitian";
    public static final String PATH_PENELITIAN_TAMBAH = "/dosen/penelitian/tambah";
    public static final String PATH_KEGIATAN_MENGAJAR = "/dosen/kegiatan-mengajar";

    private static Dotenv loadEnv() {
        try {
            return Dotenv.configure().ignoreIfMissing().load();
        } catch (Exception e) {
            return null;
        }
    }

    private static String get(String key, String def) {
        String value = (ENV != null) ? ENV.get(key) : null;
        if (value == null) value = System.getenv(key);
        return (value != null && !value.isBlank()) ? value : def;
    }

    private TestConfig() {}
}
