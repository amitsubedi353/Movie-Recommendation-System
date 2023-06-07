package com.movie.recommendation.service;

import com.movie.recommendation.dto.MovieDto;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface WatchlistService {
    Map<Integer,String> addMovieToWatchList(Long movieId, Principal principal);
    Map<Integer,String> removeMovieFromWatchList(Long movieId,Principal principal);
    List<MovieDto>  viewAllMovieInWatchListByUser(Principal principal);

}
