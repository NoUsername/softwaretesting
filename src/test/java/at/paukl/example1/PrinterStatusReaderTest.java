package at.paukl.example1;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Paul Klingelhuber
 */
public class PrinterStatusReaderTest {

    PrinterStatusReader printerStatusReader = new PrinterStatusReader(new PrinterStatusParser(), this::supplyStream);
    private String testString = "";
    private boolean invoked = false;

    @Test
    public void readStatus() throws Exception {
        testString = "warning: foo";

        final SystemStatus status = printerStatusReader.readStatus();

        assertThat(invoked)
                .isTrue();
        assertThat(status.getStatus())
                .isEqualTo(SystemStatus.Status.WARNING);
    }

    InputStream supplyStream() {
        invoked = true;
        return new ByteArrayInputStream(testString.getBytes());
    }

}