package com.movie.recommendation.security;

import com.movie.recommendation.model.User;
import com.movie.recommendation.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepository.findByUserEmail(username);
        CustomUserDetail customUserDetail;
        if(user==null){
            throw new UsernameNotFoundException("No user found!!!");
        }else{
           customUserDetail=new CustomUserDetail(user);
        }
        return customUserDetail;
    }
}
