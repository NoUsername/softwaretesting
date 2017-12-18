package at.paukl.example1;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
public class PrinterStatusReader {
    private static final Logger LOG = getLogger(PrinterStatusReader.class);
    public static final String BASE_PATH = "C:/temp/systemstatus";

    private final PrinterStatusParser printerStatusParser;
    private Supplier<InputStream> inputStreamSupplier;

    public PrinterStatusReader(PrinterStatusParser printerStatusParser) {
        this.printerStatusParser = printerStatusParser;
        inputStreamSupplier = this::fromFile;
    }

    public PrinterStatusReader(PrinterStatusParser printerStatusParser, Supplier<InputStream> streamSupplier) {
        this.printerStatusParser = printerStatusParser;
        inputStreamSupplier = streamSupplier;
    }

    public SystemStatus readStatus() {
        try (LineIterator lineIterator = read(inputStreamSupplier.get())) {
            String line = "";
            while (lineIterator.hasNext()) {
                line = lineIterator.next();
            }

            return printerStatusParser.parseLine(line);
        } catch (IOException e) {
            LOG.warn("cannot read status file: {}", e.getMessage(), e);
            return SystemStatus.warning("Cannot read status");
        }
    }

    private LineIterator read(InputStream inputStream) throws IOException {
        return IOUtils.lineIterator(inputStream, StandardCharsets.UTF_8);
    }

    private InputStream fromFile() {
        final File file = new File(BASE_PATH, "status.txt");
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
