package com.movie.recommendation.service;

import com.movie.recommendation.model.Movie;

import java.util.List;

public interface MovieService {
    Movie createMovie(Long userId, Long genreId,Movie movie) throws Exception;

    String deleteMovie(Long userId,Long genreId,Long movieId);

    List<Movie> getAllMovieByGenre(Long userId,Long genreId);

    List<Movie> getAllMovieByUser(Long userId);

    Movie updateMovie(Long userId,Long genreId,Long movieId,Movie movie) throws Exception;


}
