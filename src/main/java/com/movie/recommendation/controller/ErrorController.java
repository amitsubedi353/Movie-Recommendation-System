package com.movie.recommendation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    public String getErrorPath(){
        return "/error";
    }
    public void globalError(HttpServletRequest request, HttpServletResponse response){
        throw new ResponseStatusException(HttpStatusCode.valueOf(response.getStatus()));
    }
}
