package at.paukl.example1;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
public class WebStatusReporter implements StatusReporter {
    private static final Logger LOG = getLogger(WebStatusReporter.class);

    public static final String URL = "http://postar.paukl.at/post/testexample"; // read http://postar.paukl.at/get/testexample
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient httpClient = new OkHttpClient();

    @Override
    public void reportStatus(SystemStatus systemStatus) {
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

}
