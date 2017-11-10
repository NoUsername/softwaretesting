package at.paukl.example3;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.function.BooleanSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author ext.pkling
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestContext.class)
public class UiTest {

    @Autowired
    private WebDriver webDriver;

    @AfterClass
    public static void end() {
        sleep(3000);
    }

    @Test
    public void testSearch() {
        webDriver.get("http://localhost:1234/");
        waitForElement(By.id("link-view"), 5000);
        final WebElement element = webDriver.findElement(By.id("link-view"));
        element.click();

        final WebElement minSpeed = webDriver.findElement(By.name("minSpeed"));

        // select all
        minSpeed.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        // overwrite
        minSpeed.sendKeys("30");

        waitDirtyIndicatorGone();

        final List<WebElement> elements = webDriver.findElements(By.cssSelector("#printer-list li"));
        for (WebElement liElement : elements) {
            assertThat(liElement.getText().trim())
                    .isNotEqualTo("foo");
        }

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
