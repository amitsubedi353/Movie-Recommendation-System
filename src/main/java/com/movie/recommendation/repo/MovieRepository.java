package com.movie.recommendation.repo;

import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.model.Movie;
import com.movie.recommendation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {


    List<Movie> findByUser(User user);
    Movie findByMovieTitle(String movieTitle);

    List<Movie> findByMovieGenre(String genre);
    @Query(value = "select m.movie_id,m.movie_full_path,m.movie_image,m.movie_description,m.movie_title,m.release_date,m.user_id_fk,m.movie_genre from movie_table m where m.movie_title like '%'title'%'",nativeQuery = true)
    List<Movie> searchMovieByTitle(@Param("title") String title);

}