package at.paukl.example2.beans;

import at.paukl.example2.domain.PrinterEntity;
import com.github.klousiaj.junit.DockerRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;


/**
 * @author ext.pkling
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles({"integrationtest", "standalone"})
public class PrinterServiceTest2 {
    private static final Logger LOG = getLogger(PrinterServiceTest2.class);

    public static final String TEST_ENTITY_NAME = "TEST_ENTITY_1";

    @ClassRule
    public static DockerRule rabbitRule =
            DockerRule.builder()
                    .image("mysql:5.7.20")
                    .ports("3316:3306")
                    .envs("MYSQL_DATABASE=testexample", "MYSQL_ROOT_PASSWORD=root")
                    .containerName("mysqlDockerUnitTest")
                    .waitForLog("mysqld: ready for connections")
                    .build();

    @Autowired
    private PrinterService printerService;

    @Test
    public void testService() {
        final Long id = printerService.saveEntity(TEST_ENTITY_NAME);
        assertThat(id)
                .isNotNull();

        final List<PrinterEntity> printerByName = printerService.findPrintersByName(TEST_ENTITY_NAME);
        assertThat(printerByName)
                .hasSize(1);
        LOG.info("test end, breakpoint-here");
    }

}