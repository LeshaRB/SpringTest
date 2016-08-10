package by.lesharb.springtest.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class HibernateConfig {

	@Autowired
	DataSource dataSource;

	private static Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();

		hibernateProperties.setProperty("org.hibernate.envers.audit_table_suffix", "_H");
		hibernateProperties.setProperty("org.hibernate.envers.revision_field_name", "AUDIT_REVISION");
		hibernateProperties.setProperty("org.hibernate.envers.revision_type_field_name", "ACTION_TYPE");
		hibernateProperties.setProperty("org.hibernate.envers.audit_strategy",
				"org.hibernate.envers.strategy.ValidityAuditStrategy");
		hibernateProperties.setProperty("org.hibernate.envers.audit_strategy_validity_end_rev_field_name",
				"AUDIT_REVISION_END");
		hibernateProperties.setProperty("org.hibernate.envers.audit_strategy_validity_store_revend_timestamp", "true");
		hibernateProperties.setProperty("org.hibernate.envers.audit_strategy_validity_revend_timestamp_field_name",
				"AUDIT_REVISION_END_TS");

		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		hibernateProperties.setProperty("hibernate.max_fetch_depth", "3");
		hibernateProperties.setProperty("hibernate.jdbc.fetch_size", "50");
		hibernateProperties.setProperty("hibernate.jdbc.batch_size", "10");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
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
	@DependsOn("dataSourceInitializer")
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
