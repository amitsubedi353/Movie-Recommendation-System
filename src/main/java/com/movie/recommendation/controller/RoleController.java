package com.movie.recommendation.controller;

import com.movie.recommendation.helper.ApiResponse;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping(path = "/create")
    ResponseEntity<ApiResponse> createRoleController(@RequestBody Role roleDto){
        String message=roleService.createRole(roleDto);
        return new ResponseEntity<>(new ApiResponse(message), HttpStatusCode.valueOf(200));
    }

}
