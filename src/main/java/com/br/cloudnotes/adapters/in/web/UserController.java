package com.br.cloudnotes.adapters.in.web;


import com.br.cloudnotes.adapters.in.web.dto.ApiResponseDto;
import com.br.cloudnotes.adapters.in.web.dto.UserRequestDto;
import com.br.cloudnotes.adapters.in.web.dto.UserResponseDto;
import com.br.cloudnotes.core.model.User;
import com.br.cloudnotes.core.ports.in.UserUseCases;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserUseCases userService;

    public UserController(UserUseCases userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto request) {
        User created = userService.createUser(request.name(), request.email());
        UserResponseDto dto = new UserResponseDto(created.getId(), created.getName(), created.getEmail());

        Map<String, ApiResponseDto.Link> links = Map.of(
                "previous", new ApiResponseDto.Link("GET", "http://localhost:8080/users", "all-users"),
                "current", new ApiResponseDto.Link("POST", "http://localhost:8080/users", "create-user"),
                "next", new ApiResponseDto.Link("GET", "http://localhost:8080/users/" + created.getId(), "get-user")
        );

        ApiResponseDto<UserResponseDto> response = new ApiResponseDto<>(201, dto, links);

        return ResponseEntity.created(URI.create("http://localhost:8080/users/" + created.getId())).body(response);
    }

}
