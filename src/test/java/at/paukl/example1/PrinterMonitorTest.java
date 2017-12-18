package at.paukl.example1;

import at.paukl.testing.Fast;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author Paul Klingelhuber
 */
@Category(Fast.class)
public class PrinterMonitorTest {

    @Ignore
    @Test(timeout = 1000)
    public void start() throws Exception {
        PrinterMonitor printerMonitor = new PrinterMonitor();

        printerMonitor.start();

        // what now?
    }

/*
NOTES:
[ ] apply SRP
[ ] apply dependency inversion
[ ] apply inversion of control

 */

}