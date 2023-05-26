package com.movie.recommendation.service;

import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.model.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface MovieService {
    String createMovie(MultipartFile file, MovieDto movie,Principal principal) throws Exception;

    String deleteMovie(Long movieId, Principal principal);

    List<MovieDto> getAllMovieByGenre(Long genreId);

    List<MovieDto> getAllMovie();

    MovieDto getMovieById(Long id);

    String  updateMovie(MovieDto movieDto,Principal principal) throws Exception;


}
