package com.example.taskapi.Controller;

import com.example.taskapi.Entity.User;
import com.example.taskapi.dto.UserCreateRequest;
import com.example.taskapi.dto.UserResponse;
import com.example.taskapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create User")
    public UserResponse createUser(@RequestBody UserCreateRequest request){
        return userService.createUser(request);
    }

    @GetMapping("/me")
    @Operation(summary = "Get My Information")
    public UserResponse getMe(@AuthenticationPrincipal User currentUser){
        return userService.toResponse(currentUser);
    }
}
