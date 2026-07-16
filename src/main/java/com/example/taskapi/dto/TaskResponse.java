package com.example.taskapi.dto;

import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        boolean completed,
        UUID userId
) { }
