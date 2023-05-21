package com.movie.recommendation.service.impl;

import com.movie.recommendation.Exception.ResourcenotFoundException;
import com.movie.recommendation.dto.UserDto;
import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.repo.UserRepository;
import com.movie.recommendation.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Data
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private QueryClass queryClass;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public UserDto createUser(UserDto  userDto) throws Exception {
        User user1 = this.userRepository.findByUserEmail(userDto.getUserEmail());

        if (user1 != null) {
            throw new Exception("User already exist with the given useremail:" + userDto.getUserEmail());
        } else {
<<<<<<< HEAD
           Optional<Role> role =roleRepository.findById(userDto.getRoleId());
           if (role.isEmpty()) {
=======
           Optional<Role> role =roleRepository.findById(userDto.getUserId());


                if (role.isEmpty()) {
>>>>>>> 68fde714264c1e4a850f8e1ce5f029e0346a5121
                    throw new Exception("User does not provide valid role for registration!!!");
                } else {
                    User resultUser=new User();
                    resultUser.setUserEmail(userDto.getUserEmail().toLowerCase());
                    resultUser.setUserPassword(bCryptPasswordEncoder.encode(userDto.getUserPassword()));
                    resultUser.setUserName(userDto.getUserName().toLowerCase());
                    resultUser.setRole(role.get());
                    resultUser = this.userRepository.save(resultUser);
                    userDto.setUserId(resultUser.getUserId());
                    return userDto;
                }
            }
            }

    @Override
    public UserDto getUserByEmail(String email) {
        User retrievedUser=this.userRepository.findByUserEmail(email);
        if(retrievedUser==null){
            throw new ResourcenotFoundException("user","email "+email,null);
        }
        UserDto resultDto=new UserDto();
        resultDto.setUserId(retrievedUser.getUserId());
        resultDto.setUserName(retrievedUser.getUserName());
        resultDto.setUserEmail(retrievedUser.getUserEmail());
        resultDto.setUserPassword(retrievedUser.getUserPassword());
        resultDto.setRoleId(retrievedUser.getRole().getRoleId());
        return resultDto;
    }

    @Override
    public String deleteUser(Long userId) {
        String message="";
        User retrievedUser=queryClass.getUserById(userId);
        retrievedUser.setRole(null);
        this.userRepository.delete(retrievedUser);
        message="User deleted successfully";
        return message;
    }

    @Override
    public String updateUser(UserDto userDto) {
        String message="";
        User retrievedUser=queryClass.getUserById(userDto.getUserId());
        if(userDto.getUserName()!=null) {
            retrievedUser.setUserName(userDto.getUserName());
        }
        if(userDto.getUserEmail()!=null) {
            retrievedUser.setUserEmail(userDto.getUserEmail());
        }
        if(userDto.getUserPassword()!=null) {
            retrievedUser.setUserPassword(retrievedUser.getUserPassword());
        }
        retrievedUser=this.userRepository.save(retrievedUser);
        if(retrievedUser!=null){
             message= "user updated successfully";
             return message;
        }

        return message;
    }
    public Boolean checkIfUserNameExists(UserDto userDto){
        return StringUtils.hasText(userDto.getUserName()) && userRepository.findByUserName(userDto.getUserName().toLowerCase())!=null;
    }
    private boolean checkIfUserEmailExists(UserDto userDto){
        return StringUtils.hasText(userDto.getUserEmail())&&userRepository.findByUserEmail(userDto.getUserEmail().toLowerCase())!=null;
    }

}
