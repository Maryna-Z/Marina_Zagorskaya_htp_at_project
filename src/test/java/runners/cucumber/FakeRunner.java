package runners.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        glue = {"tests.fake"},
        features = {
                "src\\test\\resources\\features\\fake\\FakeOne.feature"
        }
)
public class FakeRunner {
}
