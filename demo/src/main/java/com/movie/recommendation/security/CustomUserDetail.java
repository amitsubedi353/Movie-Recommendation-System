package com.movie.recommendation.security;

import com.movie.recommendation.model.Authority;
import com.movie.recommendation.model.User;
import com.movie.recommendation.model.userRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
        Set<Authority> set=new HashSet<>();
        List<userRole> userRoles =this.user.getUserRoles();
        userRoles.forEach(userRole -> {
            set.add(new Authority(userRole.getRole().getRoleName()));
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
