package com.br.cloudnotes.config;

import com.br.cloudnotes.core.ports.in.UserUseCases;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    UserUseCases userService() {
        return Mockito.mock(UserUseCases.class);
    }
}

