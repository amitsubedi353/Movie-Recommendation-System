package com.movie.recommendation.repo;

import com.movie.recommendation.model.Movie;
import com.movie.recommendation.model.Rating;
import com.movie.recommendation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating,Long> {
    List<Rating> findByMovie(Movie movie);
    @Query("select r from rating r where r.user=?1 and r.movie=?2")
    Rating findByUserAndMovie(User user,Movie movie);
    @Query("select avg(rating_number) from rating rt inner join rt.movie m  on rt.movie=m.movieId group by m.movieId where m.movieId=?1")
    Float calculateAverageRatingForMovie(Integer movieId);



}
