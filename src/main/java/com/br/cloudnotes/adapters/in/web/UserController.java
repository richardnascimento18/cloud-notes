package com.br.cloudnotes.adapters.in.web;


import com.br.cloudnotes.core.model.User;
import com.br.cloudnotes.core.ports.in.UserUseCases;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserUseCases userService;

    public UserController(UserUseCases userService) {
        this.userService = userService;
    }

    record UserRequest(String name, String email) {}

    @PostMapping
    public User create(@RequestBody UserRequest request) {
        return userService.createUser(request.name(), request.email());
    }
}
