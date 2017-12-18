package at.paukl.example1;

/**
 * @author Paul Klingelhuber
 */
public class PrinterStatusParser {

    SystemStatus parseLine(String line) {
        if (line.contains("error")) {
            return SystemStatus.error(line);
        } else if (line.contains("warn")) {
            return SystemStatus.warning(line);
        } else {
            return SystemStatus.ok(line);
        }
    }
}
