package com.slonsystems.itemsOrg.AuthMicroservice;

import com.slonsystems.itemsOrg.AuthMicroservice.tokens.TestTokenGenerationStrategy;
import com.slonsystems.itemsOrg.AuthMicroservice.tokens.TokenGenerationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class TestConfiguration {

    @Primary
    @Bean(destroyMethod = "shutdown")
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .setName("Users").build();
    }

    @Bean
    public TokenGenerationStrategy tokenGen(){
        return new TestTokenGenerationStrategy();
    }

//    @Bean
//    public DataSourceInitializer init(DataSource dataSource) {
//        DataSourceInitializer init = new DataSourceInitializer();
//        init.setDataSource(dataSource);
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.setScripts(new ClassPathResource("schema.sql"));
//        init.setDatabasePopulator(populator);
//        return init;
//    }
}
