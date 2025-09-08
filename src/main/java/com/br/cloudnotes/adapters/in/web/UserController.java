package com.br.cloudnotes.adapters.in.web;

import com.br.cloudnotes.adapters.in.web.dto.ApiResponseDto;
import com.br.cloudnotes.adapters.in.web.dto.UserRequestDto;
import com.br.cloudnotes.adapters.in.web.dto.UserResponseDto;
import com.br.cloudnotes.core.model.User;
import com.br.cloudnotes.core.ports.in.UserUseCases;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserUseCases userService;

    public UserController(UserUseCases userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponseDto<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request) {
        User created = userService.createUser(request.name(), request.email());
        UserResponseDto dto = new UserResponseDto(created.getId(), created.getName(), created.getEmail());

        Map<String, ApiResponseDto.Link> links = new LinkedHashMap<>();
        links.put("previous", new ApiResponseDto.Link("GET", "http://localhost:8080/users", "all-users"));
        links.put("current", new ApiResponseDto.Link("POST", "http://localhost:8080/users", "create-user"));
        links.put("next", new ApiResponseDto.Link("GET", "http://localhost:8080/users/" + created.getId(), "get-user"));

        return new ApiResponseDto<>(201, dto, links);
    }

    @GetMapping
    public ApiResponseDto<List<UserResponseDto>> getAllUsers(@RequestParam() int page) {
        List<User> users = userService.getAllUsers(page);
        List<UserResponseDto> dtos = users.stream().map(u -> new UserResponseDto(u.getId(), u.getName(), u.getEmail())).toList();

        String previous = "page-" + (page - 1);
        String next = "page-" + (page + 1);

        Map<String, ApiResponseDto.Link> links = new LinkedHashMap<>();
        links.put("previous", new ApiResponseDto.Link("GET", "http://localhost:8080/users/" + previous, previous));
        links.put("current", new ApiResponseDto.Link("POST", "http://localhost:8080/users/" + page, "page-" + page));
        links.put("next", new ApiResponseDto.Link("GET", "http://localhost:8080/users/" + next, next));

        return new ApiResponseDto<>(201, dtos, links);
    }

}
