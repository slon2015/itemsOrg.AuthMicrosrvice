package com.slonsystems.itemsOrg.AuthMicroservice.config;

import com.slonsystems.itemsOrg.AuthMicroservice.auth.AuthStrategy;
import com.slonsystems.itemsOrg.AuthMicroservice.auth.defaultAuthStrategy;
import com.slonsystems.itemsOrg.AuthMicroservice.repositories.UserRepository;
import com.slonsystems.itemsOrg.AuthMicroservice.tokens.TestTokenGenerationStrategy;
import com.slonsystems.itemsOrg.AuthMicroservice.tokens.TokenGenerationStrategy;
import com.slonsystems.itemsOrg.AuthMicroservice.utils.UserTemplates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public AuthStrategy authStrategy(UserRepository repository, TokenGenerationStrategy tokenGen
            , UserTemplates userTemplates){
        return new defaultAuthStrategy(repository,tokenGen,userTemplates);
    }

}
