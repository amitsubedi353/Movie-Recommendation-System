package com.movie.recommendation.service;

import com.movie.recommendation.dto.UserDto;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user) throws Exception;
    UserDto getUserByEmail(String email);

    String deleteUser(Long userId);

    String updateUser(UserDto userDto);
}
