package com.movie.recommendation.controller;

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
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;


    @PostMapping("/create")
    ResponseEntity<User> createUser(@RequestBody User user) throws Exception {
        List<Role> roles=new ArrayList<Role>();
        Role role=new Role();


        role.setRoleName("NORMAL");
        roles.add(role);


        User resultedUser;


            //user.setRoleList(roleList);
             resultedUser = this.userService.createUser(user,roles);

        return new ResponseEntity<>(resultedUser, HttpStatusCode.valueOf(200));


    }
    @GetMapping("/getByEmail")
    ResponseEntity<User> getUserByEmail(@RequestParam("userEmail")String userEmail){
        User resultUser=this.userService.getUserByEmail(userEmail);
        return new ResponseEntity<>(resultUser,HttpStatusCode.valueOf(200));
    }
    @DeleteMapping("/delete")
    ResponseEntity<ApiResponse> deleteUser(@RequestParam("userId")Long userId){
        String message=this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse(message),HttpStatusCode.valueOf(200));
    }

    @PutMapping("/update")
    ResponseEntity<User> updateUser(@RequestParam("userId")Long userId,@RequestBody User user){
        User updateUser=this.userService.updateUser(userId,user);
        return new ResponseEntity<>(updateUser,HttpStatusCode.valueOf(200));
    }




}
