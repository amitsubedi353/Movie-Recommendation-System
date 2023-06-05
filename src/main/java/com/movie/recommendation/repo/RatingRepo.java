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

    @Query(value = "select avg(rt.rating_number) from rating_table rt inner join  movie_table m  on rt.movie_id_fk=m.movie_id inner join user_table u on u.user_id=m.user_id_fk  where rt.movie_id_fk=?1 group by rt.movie_id_fk",nativeQuery = true)
    Float calculateAverageRatingForMovie(Long movieId);

    @Query(value = "select r.rating_id,r.rating_number,r.rating_post_date,r.movie_id_fk,r.user_id_fk from rating_table r inner join user_table u on u.user_id=r.user_id_fk inner join movie_table m on m.movie_id=r.movie_id_fk where r.user_id_fk=?1 and r.movie_id_fk=?2",nativeQuery = true)
    Rating getRatingByUserAndMovie(Long userId,Long movieId);



}