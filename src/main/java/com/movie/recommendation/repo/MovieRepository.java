package com.movie.recommendation.repo;

import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.model.Movie;
import com.movie.recommendation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {


    List<Movie> findByUser(User user);
    Movie findByMovieTitle(String movieTitle);

    List<Movie> findByMovieGenre(String genre);

}