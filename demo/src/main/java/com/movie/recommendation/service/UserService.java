package com.movie.recommendation.service;

import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user,List<Role> roles) throws Exception;
    User getUserByEmail(String email);

    String deleteUser(Long userId);

    User updateUser(Long userId,User updatedUser);
}
