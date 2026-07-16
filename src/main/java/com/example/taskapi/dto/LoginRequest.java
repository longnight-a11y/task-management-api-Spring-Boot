package com.example.taskapi.dto;

public record LoginRequest(
        String username,
        String password
) { }
