package com.example.taskapi.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username
) { }
