package at.paukl.junit5demo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author ext.pkling
 */
public class Junit5Parameterized {
    private static final Logger LOG = getLogger(Junit5Parameterized.class);
    public static final String TEST_FILE = "/junit5test.csv";

    @ParameterizedTest
    @CsvFileSource(resources = TEST_FILE)
    public void testFeature1(String customerName) {
        LOG.info("testing feature 1 for customer {}", customerName);

        assertThat(true)
        .isTrue();
    }

    @ParameterizedTest
    @CsvFileSource(resources = TEST_FILE)
    public void testFeature2(String customerName) {
        LOG.info("testing feature 2 for customer {}", customerName);

        assertThat(true)
                .isTrue();
    }

}
