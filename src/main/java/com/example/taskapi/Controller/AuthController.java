package com.example.taskapi.Controller;

import com.example.taskapi.dto.LoginRequest;
import com.example.taskapi.dto.LoginResponse;
import com.example.taskapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }
}
