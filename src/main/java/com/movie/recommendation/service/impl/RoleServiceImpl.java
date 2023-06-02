package com.movie.recommendation.service.impl;

import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private QueryClass queryClass;





    @Override
    public String createRole(Role role) {
        Role retrievedRole=roleRepository.findByRoleName(role.getRoleName());
        if(retrievedRole!=null){
            throw new RuntimeException("Role with the given name already exists!!!!");
        }
        roleRepository.save(role);
        return "role created successfully!!!!";
    }

    @Override
    public Role updateRole(Role roleDto, Long roleId) throws Exception {
        return null;
    }
}
