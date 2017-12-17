package at.paukl.example3;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.function.BooleanSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
@SpringBootTest
@OverrideAutoConfiguration(enabled = false)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContext.class)
@Tag("endToEnd")
public class DemoUiTest {
    private static final Logger LOG = getLogger(DemoUiTest.class);

    @Value("${testing.url:http://localhost:1234/}")
    private String baseUrl;

    @Autowired
    private WebDriver webDriver;

    @AfterAll
    public static void end() {
        // just to see the result in the browser before it closes
        sleep(3000);
    }

    @Test
    public void testSearch() {
        LOG.info("testing against {}", baseUrl);
        webDriver.get(baseUrl);
        ScreenshotUtil.takeScreenshot(webDriver, "initial");
        waitForElement(By.id("link-view"), 5000);
        final WebElement element = webDriver.findElement(By.id("link-view"));
        element.click();

        final WebElement minSpeed = webDriver.findElement(By.name("minSpeed"));

        // select all
        minSpeed.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        // overwrite
        minSpeed.sendKeys("30");

        waitDirtyIndicatorGone();

        LOG.info("data reloaded...");

        final List<WebElement> elements = webDriver.findElements(By.cssSelector("#printer-list li"));
        for (WebElement liElement : elements) {
            try {
                assertThat(liElement.getText().trim())
                        .isNotEqualTo("foo");
            } catch (Throwable e) {
                LOG.warn("failed: " + e.getMessage(), e);
                ScreenshotUtil.takeScreenshot(webDriver, "fail");
                throw e;
            }
        }
        ScreenshotUtil.takeScreenshot(webDriver, "success");

        sleep(5000);
    }


    private void waitDirtyIndicatorGone() {
        waitElementsInvisible(By.cssSelector("#dirty-indicator"), 2000);
    }

    private void waitElementsInvisible(By by, int time) {
        waitFor(() -> {
            final List<WebElement> elements = webDriver.findElements(by);
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return false;
                }
            }
            return true;
        }, time, "element " + by.toString() + " not invisible in time");
    }

    private void waitForElementGone(By by, int millis) {
        waitFor(webDriver.findElements(by)::isEmpty, millis, "element " + by.toString() + " not found in time");
    }

    private void waitForElement(By by, int millis) {
        waitFor(() -> !webDriver.findElements(by).isEmpty(), millis, "element " + by.toString() + " not found in time");
    }

    private void waitFor(BooleanSupplier isOk, int millis, String failMessage) {
        long start = System.currentTimeMillis();
        while (start + millis > System.currentTimeMillis()) {
            if (isOk.getAsBoolean()) {
                return;
            }
            sleep(100);
        }
        fail(failMessage);
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

/*
TODO: refactor to use page abstraction pattern
 */