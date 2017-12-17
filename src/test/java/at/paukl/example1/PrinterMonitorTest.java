package at.paukl.example1;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

/**
 * @author Paul Klingelhuber
 */
@Tag("fast")
public class PrinterMonitorTest {

    @Disabled
    @Test
    public void start() throws Exception {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            PrinterMonitor printerMonitor = new PrinterMonitor();

            printerMonitor.start();

            // what now?
        });
    }

    @Test
    public void dummyTest() throws Exception {
        assertThat(true).isTrue();
    }

/*
NOTES:
[ ] apply SRP
[ ] apply dependency inversion
[ ] apply inversion of control

 */

}