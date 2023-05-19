package com.movie.recommendation.security;

import com.movie.recommendation.model.Authority;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomUserDetail implements UserDetails {
    @Autowired
    private User user;

    public CustomUserDetail(User user){
        this.user=user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> set=new HashSet<>();
        Role userRole =this.user.getRole();
        List<Role> roles=new ArrayList<>();
        roles.add(userRole);
        roles.forEach(eachRole -> {
            set.add(new Authority(eachRole.getRoleName()));
        });
        return set;
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
