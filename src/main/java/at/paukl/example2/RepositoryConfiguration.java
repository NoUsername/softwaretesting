package at.paukl.example2;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author ext.pkling
 */
@Configuration
@Profile("standalone")
@EnableJpaRepositories
public class RepositoryConfiguration {
}
