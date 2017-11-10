package at.paukl.example1;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
public class PrinterMonitor {
    private static final Logger LOG = getLogger(PrinterMonitor.class);
    public static final int MONITOR_INTERVAL_MS = 2000;
    private boolean active = true;

    public static final String URL = "http://postar.paukl.at/post/testexample"; // read http://postar.paukl.at/get/testexample
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient httpClient = new OkHttpClient();

    private PrinterStatusReader statusReader = new PrinterStatusReader();

    private void postStatus(SystemStatus systemStatus) {
        try {
            Request request = new Request.Builder()
                    .url(URL)
                    .post(RequestBody.create(JSON, objectMapper.writeValueAsBytes(systemStatus)))
                    .build();

            final Response response = httpClient.newCall(request)
                    .execute();
            if (!response.isSuccessful()) {
                LOG.warn("server returned error: {}", response.body().string());
            }
        } catch (Exception e) {
            LOG.warn("error sending http status: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void start() {
        final LocalDateTime started = LocalDateTime.now();
        while (active) {
            SystemStatus systemStatus = statusReader.readStatus();
            postStatus(systemStatus);

            LOG.info("still running after {}", DurationFormatUtils
                    .formatDuration(Duration.between(started, LocalDateTime.now()).toMillis(),
                            "HH'h' mm'm' ss's'"));
            try {
                Thread.sleep(MONITOR_INTERVAL_MS);
            } catch (InterruptedException e) {
                LOG.warn("interrupted {}", e.getMessage(), e);
                active = false;
            }
        }
    }

    public static void main(String[] args) {
        new PrinterMonitor().start();
    }

}
