package com.movie.recommendation.controller;


import com.movie.recommendation.helper.JwtHelper;
import com.movie.recommendation.helper.JwtRequest;
import com.movie.recommendation.helper.JwtResponse;
import com.movie.recommendation.security.CustomUserDetail;
import com.movie.recommendation.security.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping("/generate-token")
    ResponseEntity<?> generateTokenController(@RequestBody JwtRequest jwtRequest) throws Exception {

        this.authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
        UserDetails userDetails =this.customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
        String token=this.jwtHelper.generateToken(userDetails);
        JwtResponse response=new JwtResponse();
        response.setToken(token);
        return new ResponseEntity<JwtResponse>(response, HttpStatusCode.valueOf(200));

    }

    private void authenticate(String username,String password) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        try{
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (DisabledException e){
            throw new Exception("user is disabled!!!");
        }
    }
}
