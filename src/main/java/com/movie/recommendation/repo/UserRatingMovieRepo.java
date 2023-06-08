package com.movie.recommendation.repo;

import com.movie.recommendation.model.UserRatingMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRatingMovieRepo extends JpaRepository<UserRatingMovie,Long> {

    @Query(value = "select u.user_id,rt.rating_id,rt.rating_number,m.movie_id from movie_table m inner join rating_table rt on rt.movie_id_fk=m.movie_id inner join user_table u on u.user_id=rt.user_id_fk inner join role_table r on r.role_id=u.role_id_fk where r.role_name='NORMAL'",nativeQuery = true)
    List<UserRatingMovie> findUserRatingMovie();


}
