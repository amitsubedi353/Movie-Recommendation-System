package com.movie.recommendation.service;

import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.model.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface MovieService {
    Map<String,Object> createMovie(MultipartFile file, MovieDto movie,Principal principal) throws Exception;

    String deleteMovie(Long movieId, Principal principal);

    List<MovieDto> getAllMovieByGenre(String genre);

    List<MovieDto> getAllMovie();

    Map<String,Object> getMovieById(Long id,Principal principal);

    Map<Integer,List<MovieDto>> recommendMovieForUser(Principal principal,int numRecommendation);
    String  updateMovie(MovieDto movieDto,Principal principal) throws Exception;


}
