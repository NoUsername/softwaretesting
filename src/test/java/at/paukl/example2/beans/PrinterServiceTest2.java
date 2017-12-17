package at.paukl.example2.beans;

import at.paukl.example2.domain.PrinterEntity;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.configuration.ProjectName;
import com.palantir.docker.compose.configuration.ShutdownStrategy;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;


/**
 * @author Paul Klingelhuber
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"integrationtest", "standalone"})
@Tag("slow")
// reuse the junit 4 docker rule for now - enable junit5 migration feature
@EnableRuleMigrationSupport
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

    @BeforeAll
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