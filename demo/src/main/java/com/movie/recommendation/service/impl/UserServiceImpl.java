package com.movie.recommendation.service.impl;

import com.movie.recommendation.Exception.ResourcenotFoundException;
import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;
import com.movie.recommendation.model.userRole;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.repo.UserRepository;
import com.movie.recommendation.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
    public User createUser(User user, List<Role> roles) throws Exception {
        List<userRole> userRoleList=new ArrayList<>();
        userRole userRole=new userRole();


        User user1 = this.userRepository.findByUserEmail(user.getUserEmail());
        List<User> user2=new ArrayList<>();

        List<Role> assignedRole=new ArrayList<>();
        if (user1 != null) {
            throw new Exception("User already exist with the given useremail:" + user.getUserEmail());
        } else {
            for (Role eachRole : roles
            ) {
                Role role = this.roleRepository.findByRoleName(eachRole.getRoleName());

                if (role == null) {
                    throw new Exception("User does not provide valid role for registration!!!");
                } else {
                    userRole.setUser(user);
                    userRole.setRole(role);
                    userRoleList.add(userRole);
                    role.setUserRoles(userRoleList);
                    user.setUserRoles(userRoleList);
                    user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));


                    user1 = this.userRepository.save(user);


                }
            }


                return user1;
            }


        }

    @Override
    public User getUserByEmail(String email) {
        User retrievedUser=this.userRepository.findByUserEmail(email);
        if(retrievedUser==null){
            throw new ResourcenotFoundException("user","email "+email,null);
        }


        return retrievedUser;
    }

    @Override
    public String deleteUser(Long userId) {
        String message="";
        User retrievedUser=queryClass.getUserById(userId);
        List<userRole> userRoles=retrievedUser.getUserRoles();
        for (userRole eachUserRole:userRoles
        ) {
            eachUserRole.setUser(null);
            eachUserRole.setRole(null);


        }




        this.userRepository.delete(retrievedUser);
        message="User deleted successfully";
        return message;
    }

    @Override
    public User updateUser(Long userId, User updatedUser) {
        User retrievedUser=queryClass.getUserById(userId);
        retrievedUser.setUserName(updatedUser.getUserName());
        retrievedUser.setUserEmail(updatedUser.getUserEmail());
        retrievedUser.setUserPassword(updatedUser.getUserPassword());
        retrievedUser=this.userRepository.save(retrievedUser);

        return retrievedUser;
    }

}
