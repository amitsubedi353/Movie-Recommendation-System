package com.movie.recommendation.repo;

import com.movie.recommendation.model.Movie;
import com.movie.recommendation.model.Rating;
import com.movie.recommendation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RatingRepo extends JpaRepository<Rating,Long> {
    List<Rating> findByMovie(Movie movie);

    @Query(value = "select avg(rt.ratingNumber) from Rating rt inner join Movie m  on rt.movie.movieId=m.movieId inner join User u on u.userId=rt.movie.movieId group by rt.movieId where rt.movie.movieId=?1",nativeQuery = true)
    Float calculateAverageRatingForMovie(Long movieId);

    @Query(value = "select r.id from Rating r inner join User u on u.userId=r.user.userId inner join Movie m on m.movieId=r.movie.movieId where r.user.userId=?1 and r.movie.movieId=?2",nativeQuery = true)
    Rating getRatingByUserAndMovie(Long userId,Long movieId);



}