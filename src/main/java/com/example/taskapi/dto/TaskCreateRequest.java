package com.example.taskapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
        @NotBlank @Size(min = 1, max = 100) String title,
        String description,
        boolean completed
) { }
