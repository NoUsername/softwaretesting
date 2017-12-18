package at.paukl.example1;

import at.paukl.testing.Fast;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static at.paukl.example1.ExampleAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Paul Klingelhuber
 */
@Category(Fast.class)
public class PrinterMonitorTest {

    @Mock
    private PrinterStatusReader statusReader;
    @Mock
    private StatusReporter statusReporter;
    @Captor
    private ArgumentCaptor<SystemStatus> statusArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(timeout = 500)
    public void start() throws Exception {
        PrinterMonitor printerMonitor = new PrinterMonitor(statusReader, statusReporter, MoreExecutors.directExecutor());
        printerMonitor.setMonitorIntervalMs(0);

        when(statusReader.readStatus()).thenAnswer((inv) -> {
            // stop monitor on first invocation
            printerMonitor.stop();
            return SystemStatus.warning("some warning");
        });

        printerMonitor.start();

        verify(statusReader).readStatus();
        verify(statusReporter).reportStatus(statusArgumentCaptor.capture());
        // custom assertion
        assertThat(statusArgumentCaptor.getValue())
                .messageContains("some")
                .hasStatus(SystemStatus.Status.WARNING);
    }

/*
NOTES:
[x] apply SRP
[x] apply dependency inversion
[x] apply inversion of control

 */

}