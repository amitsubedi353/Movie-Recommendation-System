package com.movie.recommendation.repo;

import com.movie.recommendation.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority,Long> {
    Authority findByAuthority(String name);

}
