package com.movie.recommendation.controller;

import com.movie.recommendation.dto.AuthorityDto;
import com.movie.recommendation.helper.ApiResponse;
import com.movie.recommendation.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;
    @PostMapping("/create")
    ResponseEntity<ApiResponse> createAuthorityController(@RequestBody AuthorityDto authorityDto){
        String message=authorityService.createAuthority(authorityDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(message));
    }
}
