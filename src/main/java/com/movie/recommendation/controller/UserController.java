package com.movie.recommendation.controller;

import com.movie.recommendation.dto.UserDto;
import com.movie.recommendation.helper.ApiResponse;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;


    @PostMapping("/create")
    ResponseEntity<UserDto> createUser(@RequestBody UserDto user) throws Exception {



            //user.setRoleList(roleList);
             UserDto userDto = this.userService.createUser(user);

        return new ResponseEntity<>(userDto, HttpStatusCode.valueOf(200));


    }
    @GetMapping("/getByEmail")
    ResponseEntity<UserDto> getUserByEmail(@RequestParam("userEmail")String userEmail){
        UserDto resultUser=this.userService.getUserByEmail(userEmail);
        return new ResponseEntity<>(resultUser,HttpStatusCode.valueOf(200));
    }
    @DeleteMapping("/delete")
    ResponseEntity<ApiResponse> deleteUser(@RequestParam("userId")Long userId){
        String message=this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse(message),HttpStatusCode.valueOf(200));
    }

    @PutMapping("/update")
    ResponseEntity<?> updateUser(@RequestBody UserDto userDto){
        String message=this.userService.updateUser(userDto);
        if(message.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(message), HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(new ApiResponse("Something went wrong"),HttpStatusCode.valueOf(500));
    }
}
