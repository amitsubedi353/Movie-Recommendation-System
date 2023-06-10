package com.movie.recommendation.service;

import com.movie.recommendation.dto.MovieDto;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface WatchlistService {
    Map<String,Object> addMovieToWatchList(Long movieId, Principal principal);
    Map<String,Object> removeMovieFromWatchList(Long movieId,Principal principal);
    Map<String,Object>  viewAllMovieInWatchListByUser(Principal principal);

}
