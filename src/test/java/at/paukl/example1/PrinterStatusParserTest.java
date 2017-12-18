package at.paukl.example1;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;


/**
 * @author Paul Klingelhuber
 */
@RunWith(JUnitParamsRunner.class)
public class PrinterStatusParserTest {

    PrinterStatusParser printerStatusParser = new PrinterStatusParser();

    @Test
    @Parameters({
            "foo, OK",
            "warn: something, WARNING",
            "war n i n g, OK",
            ", OK",
            "error, ERROR",
            "error: foobar 1234, ERROR",
            "errorRRRRRR everything is burning, ERROR",
    })
    public void parseLine(String line, SystemStatus.Status expectedStatus) throws Exception {
        final SystemStatus result = printerStatusParser.parseLine(line);

        assertThat(tuple(result.getMessage(), result.getStatus()))
                .isEqualTo(tuple(line, expectedStatus));
    }

}