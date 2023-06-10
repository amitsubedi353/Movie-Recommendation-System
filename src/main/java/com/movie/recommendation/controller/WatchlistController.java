package com.movie.recommendation.controller;

import com.movie.recommendation.service.MovieService;
import com.movie.recommendation.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {
    @Autowired
    private WatchlistService watchlistService;
    @Autowired
    private MovieService movieService;
    @PostMapping("/add/{movieId}")
    @PreAuthorize("hasAuthority('add_to_watchlist')")
    ResponseEntity<?> addToWatchList(@PathVariable Long movieId, Principal principal){
        Map<String,Object> watchList=watchlistService.addMovieToWatchList(movieId, principal);
        return ResponseEntity.status(HttpStatus.OK).body(watchList);
    }
    @DeleteMapping("/remove/{movieId}")
    @PreAuthorize("hasAuthority('remove_from_watchlist')")
    ResponseEntity<?> removeFromWatchlist(@PathVariable Long movieId,Principal principal){
        Map<String,Object> watchList=watchlistService.removeMovieFromWatchList(movieId, principal);
        return ResponseEntity.status(HttpStatus.OK).body(watchList);
    }

@GetMapping("/view")
@PreAuthorize("hasAuthority('view_watchlist')")
ResponseEntity<?> viewAllMovieFromWatchlist(Principal principal){
        Map<String,Object> message=watchlistService.viewAllMovieInWatchListByUser(principal);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
