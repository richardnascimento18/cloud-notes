package com.br.cloudnotes.adapters.in.web;


import com.br.cloudnotes.core.model.User;
import com.br.cloudnotes.core.ports.in.CreateUserUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CreateUserUseCase createUser;

    public UserController(CreateUserUseCase createUser) {
        this.createUser = createUser;
    }

    @PostMapping
    public User create(@RequestBody UserRequest request) {
        return createUser.createUser(request.name(), request.email());
    }

    record UserRequest(String name, String email) {}
}
