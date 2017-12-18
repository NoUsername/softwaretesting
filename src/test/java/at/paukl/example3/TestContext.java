package at.paukl.example3;

import at.paukl.testing.EndToEnd;
import com.google.common.collect.ImmutableMap;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Arrays;

/**
 * @author Paul Klingelhuber
 */
public class TestContext {

    @Bean(destroyMethod = "stop")
    public ChromeDriverService chromeDriverService() throws Exception {
        File driver = new File("C:/temp/chromedriver.exe");
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(driver)
                .usingAnyFreePort()
                .build();
        service.start();
        return service;
    }

    @Bean(destroyMethod = "quit")
    public WebDriver webDriver(ChromeDriverService service) {
        final DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        // make headless:
        //capabilities.setCapability("chromeOptions", ImmutableMap.of("args", Arrays.asList("--headless")));
        RemoteWebDriver driver = new RemoteWebDriver(service.getUrl(),
                capabilities);
        return driver;
    }

}
