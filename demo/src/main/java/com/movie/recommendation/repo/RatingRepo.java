package com.movie.recommendation.repo;

import com.movie.recommendation.model.Movie;
import com.movie.recommendation.model.Rating;
import com.movie.recommendation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating,Long> {
    List<Rating> findByMovie(Movie movie);
    Rating findByUser(User user);

}
