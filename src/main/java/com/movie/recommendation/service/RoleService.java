package com.movie.recommendation.service;

import com.movie.recommendation.model.Role;

public interface RoleService {
    String createRole(Role  roleDto);
    Role updateRole(Role roleDto,Long roleId) throws Exception;
}
