package com.movie.recommendation.controller;

import com.movie.recommendation.dto.UserDto;
import com.movie.recommendation.helper.ApiResponse;
<<<<<<< HEAD
import com.movie.recommendation.helper.JwtHelper;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.repo.UserRepository;
import com.movie.recommendation.security.CustomUserDetail;
import com.movie.recommendation.security.CustomUserDetailService;
import com.movie.recommendation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
=======
import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
>>>>>>> 68fde714264c1e4a850f8e1ce5f029e0346a5121
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Collection;
=======
>>>>>>> 68fde714264c1e4a850f8e1ce5f029e0346a5121
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
<<<<<<< HEAD
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserRepository userRepository;
=======
>>>>>>> 68fde714264c1e4a850f8e1ce5f029e0346a5121


    @PostMapping("/create")
    ResponseEntity<UserDto> createUser(@RequestBody UserDto user) throws Exception {
<<<<<<< HEAD
        //user.setRoleList(roleList);
=======



            //user.setRoleList(roleList);
>>>>>>> 68fde714264c1e4a850f8e1ce5f029e0346a5121
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
<<<<<<< HEAD
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserDto userDto) throws Exception {
        String userName = userDto.getUserEmail();
        String password = userDto.getUserPassword();
        UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
        authenticate(userName, password, userDetails.getAuthorities());
        User retrievedUser=userRepository.findByUserEmail(userName);
        String jwtToken = this.jwtHelper.generateToken(new CustomUserDetail(retrievedUser));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);
        ApiResponse apiResponse = new ApiResponse("Token generated successfully");
        return ResponseEntity.ok().headers(headers).body(apiResponse);

    }

    private void authenticate(String userName, String password, Collection<? extends GrantedAuthority> authorities)
            throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userName, password, authorities);
        try {
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (DisabledException e) {
            throw new Exception("user is disabled!!!");
        }
    }

=======
>>>>>>> 68fde714264c1e4a850f8e1ce5f029e0346a5121
}
