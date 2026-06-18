package runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

/**
 * CucumberRunner — JUnit Platform Suite runner untuk test suite modul Dosen (PAK) SIA UGN.
 * JUnit Platform akan menemukan & menjalankan seluruh skenario Cucumber otomatis.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "steps, hooks"
)
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-reports/cucumber-report.html, json:target/cucumber-reports/cucumber-report.json"
)
@ConfigurationParameter(
        key = FILTER_TAGS_PROPERTY_NAME,
        value = "not @Wip"
)
@ConfigurationParameter(
        key = FEATURES_PROPERTY_NAME,
        value = "src/test/resources/features"
)
public class CucumberRunner {
    // sengaja dikosongkan
}

