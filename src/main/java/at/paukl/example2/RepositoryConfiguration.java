package at.paukl.example2;

import at.paukl.example2.beans.PrinterRepository;
import at.paukl.example2.domain.PrinterEntity;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Paul Klingelhuber
 */
@Configuration
@Profile("standalone")
@ImportAutoConfiguration(classes = HibernateJpaAutoConfiguration.class)
@EnableJpaRepositories(basePackageClasses = {PrinterRepository.class})
@EntityScan(basePackageClasses = PrinterEntity.class)
public class RepositoryConfiguration {
}
