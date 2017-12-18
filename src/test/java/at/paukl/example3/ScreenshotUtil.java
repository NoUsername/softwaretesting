package at.paukl.example3;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
public class ScreenshotUtil {
    private static final Logger LOG = getLogger(ScreenshotUtil.class);

    public static void takeScreenshot(WebDriver driver, String tag) {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        try {
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            dateTime = dateTime.replaceAll(":", "-").replaceAll(" ", "_");
            FileUtils.copyFile(scrFile, new File(String.format("./build/%s-%s-screenshot.png",
                    dateTime,
                    tag)));
        } catch (IOException e) {
            LOG.warn("could not copy file: " + e.getMessage(), e);
        }
    }
}
