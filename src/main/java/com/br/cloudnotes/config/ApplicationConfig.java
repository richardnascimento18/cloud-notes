package com.br.cloudnotes.config;

import com.br.cloudnotes.core.ports.in.CreateUserUseCase;
import com.br.cloudnotes.core.ports.out.UserRepositoryPort;
import com.br.cloudnotes.core.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    CreateUserUseCase createUserUseCase(UserRepositoryPort userRepository) {
        return new UserService(userRepository);
    }
}
