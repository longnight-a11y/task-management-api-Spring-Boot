package com.example.taskapi.dto;

import java.util.UUID;

public record TaskResponseWithUser(
        UUID id,
        String title,
        String description,
        boolean completed,
        UserResponse user
) { }
