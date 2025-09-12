package com.br.cloudnotes.adapters.in.web;

import com.br.cloudnotes.adapters.in.web.dto.request.UserRequestDto;
import com.br.cloudnotes.config.TestConfig;
import com.br.cloudnotes.core.model.User;
import com.br.cloudnotes.core.ports.in.UserUseCases;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // comes from Spring Boot Test

    @Autowired
    private UserUseCases userService; // mock the service layer

    @Test
    void createUser_shouldReturn201AndUserResponse() throws Exception {
        // Arrange
        User fakeUser = new User(UUID.randomUUID(), "John", "john@example.com");
        when(userService.createUser(any(), any())).thenReturn(fakeUser);

        UserRequestDto requestDto = new UserRequestDto("John", "john@example.com");
        String json = objectMapper.writeValueAsString(requestDto);

        // Act + Assert
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.body.name").value("John"))
                .andExpect(jsonPath("$.body.email").value("john@example.com"))
                .andExpect(jsonPath("$._links.current.rel").value("create-user"));
    }

    @Test
    void createUser_shouldReturn400WhenInvalidName() throws Exception {
        // Invalid name (does not match regex)
        UserRequestDto requestDto = new UserRequestDto("john", "john@example.com");
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.body.error").value("ONLY_FIRST_NAME_WITH_FIRST_LETTER_UPPERCASE"));
    }

    @Test
    void createUser_shouldReturn409WhenEmailExists() throws Exception {
        // Arrange: simulate conflict
        when(userService.createUser(any(), any()))
                .thenThrow(new com.br.cloudnotes.core.domain.exceptions.UserAlreadyExistsException("john@example.com"));

        UserRequestDto requestDto = new UserRequestDto("John", "john@example.com");
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.body.error").value("USER_john@example.com_ALREADY_EXISTS"));
    }
}
