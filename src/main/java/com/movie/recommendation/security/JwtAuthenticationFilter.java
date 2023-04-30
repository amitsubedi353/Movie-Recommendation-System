package com.movie.recommendation.security;

import com.movie.recommendation.helper.JwtHelper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailService  customUserDetailService;
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            final String requestTokenHeader=request.getHeader("Authorization");
            String username=null;
            String jwtToken=null;
            if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
                jwtToken=requestTokenHeader.substring(7);

                try{
                    username=this.jwtHelper.extractUsername(jwtToken);
                }catch (ExpiredJwtException e){
                  e.printStackTrace();
                  System.out.println("jwt has expired");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else {
                System.out.println("Invalid token, not start with bearer string");
            }

            //validated
        if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
            final UserDetails userDetails=this.customUserDetailService.loadUserByUsername(username);
            if(this.jwtHelper.validateToken(jwtToken,userDetails)){
               //token is valid
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            }

        }else {
            System.out.println("Token is not valid!!!");
        }
        filterChain.doFilter(request,response);

    }
}
