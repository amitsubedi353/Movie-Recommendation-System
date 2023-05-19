package com.movie.recommendation.repo;

import com.movie.recommendation.model.Movie;
import com.movie.recommendation.model.Rating;
import com.movie.recommendation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating,Long> {
    List<Rating> findByMovie(Movie movie);
    @Query("select r from Rating r where r.user=?1 and r.movie=?2")
    Rating findByUserAndMovie(User user,Movie movie);
    //@Query(value = "select avg(rt.rating_number) from Rating rt inner join rt.Movie m  on  m.movie_id=rt. group by m.movieId where m.movieId=?1",nativeQuery = true)
    @Query(value = "select av from  Rating r inner join r.movie m on r.movie_id=m.movieId group by r.movie_id where r.movie_id=?1",nativeQuery = true)
    Float calculateAverageRatingForMovie(Integer movieId);



}
