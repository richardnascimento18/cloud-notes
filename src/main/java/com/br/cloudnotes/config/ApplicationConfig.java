package com.br.cloudnotes.config;

import com.br.cloudnotes.core.ports.in.NoteUseCases;
import com.br.cloudnotes.core.ports.in.UserUseCases;
import com.br.cloudnotes.core.ports.out.NoteRepositoryPort;
import com.br.cloudnotes.core.ports.out.UserRepositoryPort;
import com.br.cloudnotes.core.service.NoteService;
import com.br.cloudnotes.core.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    UserUseCases userUseCases(UserRepositoryPort userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    NoteUseCases noteUseCases(NoteRepositoryPort noteRepository) {
        return new NoteService(noteRepository);
    }
}
