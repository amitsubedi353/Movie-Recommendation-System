package com.movie.recommendation.service;

import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.model.Movie;

import java.security.Principal;
import java.util.List;

public interface MovieService {
    MovieDto createMovie(MovieDto movieDto,Principal principal) throws Exception;

    String deleteMovie(Long movieId, Principal principal);

    List<MovieDto> getAllMovieByGenre(Long genreId);

    List<MovieDto> getAllMovie();

    String  updateMovie(MovieDto movieDto,Principal principal) throws Exception;


}
