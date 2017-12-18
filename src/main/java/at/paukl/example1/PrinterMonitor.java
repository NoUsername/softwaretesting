package at.paukl.example1;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
public class PrinterMonitor {
    private static final Logger LOG = getLogger(PrinterMonitor.class);
    public int monitorIntervalMs = 2000;
    private boolean active = true;

    private final PrinterStatusReader statusReader;
    private final StatusReporter statusReporter;
    private final Executor executor;

    public PrinterMonitor(PrinterStatusReader statusReader, StatusReporter statusReporter, Executor executor) {
        this.statusReader = statusReader;
        this.statusReporter = statusReporter;
        this.executor = executor;
    }

    public void setMonitorIntervalMs(int monitorIntervalMs) {
        this.monitorIntervalMs = monitorIntervalMs;
    }

    public void start() {
        executor.execute(() -> runWhileActive(this::readAndReport));
    }

    void readAndReport() {
        SystemStatus systemStatus = statusReader.readStatus();
        statusReporter.reportStatus(systemStatus);
    }

    void runWhileActive(Runnable runnable) {
        final LocalDateTime started = LocalDateTime.now();
        while (active) {
            runnable.run();

            LOG.info("still running after {}", DurationFormatUtils
                    .formatDuration(Duration.between(started, LocalDateTime.now()).toMillis(),
                            "HH'h' mm'm' ss's'"));
            try {
                Thread.sleep(monitorIntervalMs);
            } catch (InterruptedException e) {
                LOG.warn("interrupted {}", e.getMessage(), e);
                active = false;
            }
        }
    }

    void stop() {
        active = false;
    }

    public static void main(String[] args) {
        StatusReporter statusReporter = new WebStatusReporter();
        PrinterStatusReader statusReader = new PrinterStatusReader(new PrinterStatusParser());
        new PrinterMonitor(statusReader, statusReporter, Executors.newSingleThreadExecutor()).start();
    }

}
