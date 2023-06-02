package com.movie.recommendation.service.impl;

import com.movie.recommendation.dto.AuthorityDto;
import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.Authority;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.repo.AuthorityRepo;
import com.movie.recommendation.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    private AuthorityRepo authorityRepo;
    @Autowired
    private QueryClass queryClass;


    @Override
    public String createAuthority(AuthorityDto authorityDto) {
        Role retrievedRole=queryClass.getRoleById(authorityDto.getRoleId());
        Authority authority=new Authority();
        authority.setAuthority(authorityDto.getAuthority());
        authority.setRole(retrievedRole);
        authorityRepo.save(authority);
        return "authority created successfully!!!";
    }
}
