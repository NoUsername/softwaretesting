package at.paukl.example3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.Bean;

import java.io.File;

/**
 * @author ext.pkling
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
        RemoteWebDriver driver = new RemoteWebDriver(service.getUrl(),
                capabilities);
        return driver;
    }

}
