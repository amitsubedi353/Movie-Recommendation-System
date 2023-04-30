package com.movie.recommendation.helper;

import lombok.Data;

@Data
public class ApiResponse {
    String message;

    public ApiResponse(String message) {
        this.message = message;
    }
}
