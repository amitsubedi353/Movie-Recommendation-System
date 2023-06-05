package com.movie.recommendation.repo;

import com.movie.recommendation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
    Genre findByGenreName(String genreName);

    List<Genre> findByUser(User user);
}
