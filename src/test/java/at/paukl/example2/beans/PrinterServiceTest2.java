package at.paukl.example2.beans;

import at.paukl.example2.domain.PrinterEntity;
import at.paukl.testing.Medium;
import at.paukl.testing.Slow;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.configuration.ProjectName;
import com.palantir.docker.compose.configuration.ShutdownStrategy;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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
 * @author Paul Klingelhuber
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles({"integrationtest", "standalone"})
@Category(Slow.class)
public class PrinterServiceTest2 {
    private static final Logger LOG = getLogger(PrinterServiceTest2.class);

    public static final String TEST_ENTITY_NAME = "TEST_ENTITY_1";

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose.yml")
            // Restart-related: if we let the rule stop the container we'll get errorMessages,
            //  because the db-conns get torn down later than the container
            //  therefore we use this stop-on-next-test-start thing... this means you should
            //  stop the containers manually afterwards.
            .removeConflictingContainersOnStartup(true)
            .projectName(ProjectName.fromString("integrationtest"))
            .shutdownStrategy(ShutdownStrategy.SKIP)
            // End restart-related.
            .waitingForService("mysqlfortest", HealthChecks.toHaveAllPortsOpen())
            .build();

    @Autowired
    private PrinterService printerService;

    @BeforeClass
    public static void initClass() throws InterruptedException {
        LOG.info("waiting for container....");
        // Really ugly but sadly DockerComposeRule doesn't have a "wait for log".
        //  The cleaner solution would of course be to write a nice "wait for log output" HealthCheck
        //  for the docker-compose testing library.
        Thread.sleep(20000);
    }

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