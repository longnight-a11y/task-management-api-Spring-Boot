package com.example.taskapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank @Size(min = 1, max = 50) String username,
        @NotBlank @Size(min = 6) String password
) { }
