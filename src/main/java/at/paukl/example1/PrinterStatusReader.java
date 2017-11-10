package at.paukl.example1;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
public class PrinterStatusReader {
    private static final Logger LOG = getLogger(PrinterStatusReader.class);
    public static final String BASE_PATH = "C:/temp/systemstatus";

    public SystemStatus readStatus() {
        final File file = new File(BASE_PATH, "status.txt");
        try (LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8")) {
            String line = "";
            while (lineIterator.hasNext()) {
                line = lineIterator.next();
            }

            if (line.contains("error")) {
                return SystemStatus.error(line);
            } else if (line.contains("warn")) {
                return SystemStatus.warning(line);
            } else {
                return SystemStatus.ok(line);
            }
        } catch (IOException e) {
            LOG.warn("cannot read status file: {}", e.getMessage(), e);
            return SystemStatus.warning("Cannot read status");
        }
    }

}
