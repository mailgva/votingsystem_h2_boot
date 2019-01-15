package com.voting.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.cache.Caching;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Properties;

//@ComponentScan("com.voting.repository")
@Configuration
//@EnableJpaRepositories
@EnableJpaRepositories(basePackages = "com.voting.repository")
@EnableTransactionManagement
public class PersistenceJPAConfig {

    @Autowired
    private DataSource dataSource;



    @ConfigurationProperties("spring.jpa.hibernate")
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);

        entityManagerFactory.setPackagesToScan("com.voting.model");



        // Vendor adapter
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        // Hibernate properties
        Properties additionalProperties = new Properties();
        additionalProperties.put("hibernate.dialect","org.hibernate.dialect.H2Dialect" /*"${dialect}"*/);
        additionalProperties.put("hibernate.show_sql", "true" /* "${show-sql}"*/);
        //additionalProperties.put("hibernate.hbm2ddl.auto","hbm2ddl.auto" /*"${hbm2ddl.auto}"*/);



        entityManagerFactory.setJpaProperties(additionalProperties);

        return entityManagerFactory;
    }
    /*public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.voting.model" });

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.show_sql", "${show-sql}");
        properties.put("hibernate.hbm2ddl.auto", "${hibernate.hbm2ddl.auto}");
        *//*properties.put("hibernate.cache.region.factory_class", "${hibernate.cache.region.factory_class}");
        properties.put("hibernate.javax.cache.provider", "${hibernate.javax.cache.provider}");

        properties.put("hibernate.cache.use_second_level_cache", "${hibernate.cache.use_second_level_cache}");
        properties.put("hibernate.cache.region.factory_class", "${hibernate.cache.region.factory_class}");*//*

        properties.put("hibernate.dialect", "${hibernate.dialect}");

        em.setJpaPropertyMap(properties);

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }*/

    /*@Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }*/

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:voting;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        return dataSource;
    }



    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

/*

    @Bean
    public CacheManager cacheManager() throws URISyntaxException {
        return new JCacheCacheManager(Caching.getCachingProvider().getCacheManager(
                getClass().getResource("cache/ehcache.xml").toURI(),
                getClass().getClassLoader()
        ));
    }
*/

}
