package com.movie.recommendation.security;

import com.movie.recommendation.model.Authority;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CustomUserDetail implements UserDetails {
    @Autowired
    private User user;

    public CustomUserDetail(User user){
        this.user=user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        Role userRole =this.user.getRole();
        List<Authority> authorityList=userRole.getAuthorities();
        for (Authority eachAuthority:authorityList
             ) {
           grantedAuthorities.add(new SimpleGrantedAuthority(eachAuthority.getAuthority()));

        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}