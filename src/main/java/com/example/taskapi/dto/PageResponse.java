package com.example.taskapi.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> items,
        int total,
        int page,
        int size
) { }
