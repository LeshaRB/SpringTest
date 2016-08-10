package by.lesharb.springtest.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class HibernateConfig {

	@Autowired	
	Environment env;
	
	@Autowired
	DataSource dataSource;

	private Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();

		hibernateProperties.setProperty("org.hibernate.envers.audit_table_suffix", env.getProperty("org.hibernate.envers.audit_table_suffix"));
		hibernateProperties.setProperty("org.hibernate.envers.revision_field_name", env.getProperty("org.hibernate.envers.revision_field_name"));
		hibernateProperties.setProperty("org.hibernate.envers.revision_type_field_name", env.getProperty("org.hibernate.envers.revision_type_field_name"));
		hibernateProperties.setProperty("org.hibernate.envers.audit_strategy",
				env.getProperty("org.hibernate.envers.audit_strategy"));
		hibernateProperties.setProperty("org.hibernate.envers.audit_strategy_validity_end_rev_field_name",
				env.getProperty("org.hibernate.envers.audit_strategy_validity_end_rev_field_name"));
		hibernateProperties.setProperty("org.hibernate.envers.audit_strategy_validity_store_revend_timestamp", env.getProperty("org.hibernate.envers.audit_strategy_validity_store_revend_timestamp"));
		hibernateProperties.setProperty("org.hibernate.envers.audit_strategy_validity_revend_timestamp_field_name",
				env.getProperty("org.hibernate.envers.audit_strategy_validity_revend_timestamp_field_name"));

		hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		hibernateProperties.setProperty("hibernate.max_fetch_depth", env.getProperty("hibernate.max_fetch_depth"));
		hibernateProperties.setProperty("hibernate.jdbc.fetch_size", env.getProperty("hibernate.jdbc.fetch_size"));
		hibernateProperties.setProperty("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
		hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		return hibernateProperties;
	}

	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.H2);
		return hibernateJpaVendorAdapter;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
//	@DependsOn("dataSourceInitializer")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
		HibernateJpaVendorAdapter vendorAdapter = jpaVendorAdapter();

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPersistenceUnitName("JpaTest");
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("by.lesharb.springtest.domain");
		factory.setDataSource(dataSource);

		factory.setJpaProperties(hibernateProperties());
		factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());

		return factory;
	}
}
