package at.paukl.example2;

import at.paukl.example2.beans.PrinterService;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
public class Main {
    private static final Logger LOG = getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final SpringApplication application = new SpringApplication(Main.class);
        application.setAdditionalProfiles("standalone");
        final ConfigurableApplicationContext ctx = application.run(args);
        final PrinterService printerService = ctx.getBean(PrinterService.class);
        initDb(printerService);
    }

    private static void initDb(PrinterService printerService) {
        LOG.info("saving...");
        final ArrayList<Long> ids = new ArrayList<>();
        if (!printerService.getAll().isEmpty()) {
            LOG.info("db has already been initialized");
            return;
        }
        for (String name : Arrays.asList("foo", "bar", "baz", "bazing", "bazinga", "blub", "blubbi", "blubbedi")) {
            ids.add(printerService.saveEntity(name));
        }
        LOG.info("saved entities, got ids: {}", ids);
    }

}