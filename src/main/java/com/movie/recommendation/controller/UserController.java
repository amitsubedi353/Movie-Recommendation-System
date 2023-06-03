package com.movie.recommendation.controller;

import com.movie.recommendation.dto.UserDto;
import com.movie.recommendation.helper.ApiResponse;

import com.movie.recommendation.helper.JwtHelper;
import com.movie.recommendation.helper.JwtRequest;
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
import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500/")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/create")
    ResponseEntity<UserDto> createUser(@RequestBody UserDto user) throws Exception {

        //user.setRoleList(roleList



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

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody JwtRequest jwtRequest) throws Exception {
        Map<String,String> message=new HashMap<>();
        String userName = jwtRequest.getUsername();
        String password =jwtRequest.getPassword();
        UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
        authenticate(userName, password, userDetails.getAuthorities());
        User retrievedUser=userRepository.findByUserEmail(userName);
        String jwtToken = this.jwtHelper.generateToken(new CustomUserDetail(retrievedUser));
        String jwt="Bearer "+jwtToken;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);
        message.put("status:","200");
        message.put("Jwt-Token: ",jwt);
        return ResponseEntity.ok().body(message);

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

}
