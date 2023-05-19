package com.movie.recommendation.security;

import com.movie.recommendation.model.Authority;
<<<<<<< HEAD
import com.movie.recommendation.model.Role;
=======
>>>>>>> 68fde714264c1e4a850f8e1ce5f029e0346a5121
import com.movie.recommendation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

<<<<<<< HEAD
import java.util.*;
=======
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
>>>>>>> 68fde714264c1e4a850f8e1ce5f029e0346a5121

public class CustomUserDetail implements UserDetails {
    @Autowired
    private User user;

    public CustomUserDetail(User user){
        this.user=user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> set=new HashSet<>();
<<<<<<< HEAD
        Role userRole =this.user.getRole();
        List<Role> roles=new ArrayList<>();
        roles.add(userRole);
        roles.forEach(eachRole -> {
            set.add(new Authority(eachRole.getRoleName()));
=======
        List<userRole> userRoles =this.user.getUserRoles();
        userRoles.forEach(userRole -> {
            set.add(new Authority(userRole.getRole().getRoleName()));
>>>>>>> 68fde714264c1e4a850f8e1ce5f029e0346a5121
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
