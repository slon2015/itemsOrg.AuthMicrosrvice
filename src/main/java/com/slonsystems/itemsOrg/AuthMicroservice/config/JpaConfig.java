package com.slonsystems.itemsOrg.AuthMicroservice.config;

import org.omg.CORBA.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter jpaVendorAdapter,
                                                                       DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("com.slonsystems.itemsOrg.AuthMicroservice.pojos");

        return emf;
    }
}
