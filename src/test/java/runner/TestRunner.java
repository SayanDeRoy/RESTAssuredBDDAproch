package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
                 glue     = "stepDefinitions",
                 plugin = "json:target/jsonReports/Report.json")
public class TestRunner {


}
