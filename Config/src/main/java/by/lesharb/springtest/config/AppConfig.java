package by.lesharb.springtest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
@Import({ DataSourceConfig.class, HibernateConfig.class })
@EnableJpaAuditing(auditorAwareRef = "auditorAwareBean")
@EnableJpaRepositories(basePackages = "by.lesharb.springtest.repository")
@EnableTransactionManagement
@ComponentScan(basePackages = { "by.lesharb.springtest.service.jpa", "by.lesharb.springtest.auditor" })
public class AppConfig {

}
