package com.movie.recommendation.controller;


import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.helper.ApiResponse;
import com.movie.recommendation.model.Movie;
import com.movie.recommendation.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('manage_movie')")
    ResponseEntity<?> createMovieController(@RequestBody MovieDto movieDto,Principal principal) throws Exception {
        MovieDto movieDto1=movieService.createMovie(movieDto,principal);
        if(movieDto1==null){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!"), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(movieDto1,HttpStatusCode.valueOf(200));

    }

    @DeleteMapping("/delete/{movieId}")
    @PreAuthorize("hasAuthority('manage_movie')")
    ResponseEntity<ApiResponse> deleteMovieController(@PathVariable("movieId")Long movieId, Principal principal){
        String message=this.movieService.deleteMovie(movieId,principal);
        if(message.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!"),HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(new ApiResponse(message),HttpStatusCode.valueOf(200));

    }


@GetMapping("read/{genreId}")
@PreAuthorize("hasAuthority('view_movie')")
    ResponseEntity<?> readMovieByGenreController(@PathVariable("genreId")Long genreId){
        List<MovieDto> movies=this.movieService.getAllMovieByGenre(genreId);
        if(movies.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("No movie for the given genre"),HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(movies,HttpStatusCode.valueOf(200));

    }

    @GetMapping("/read/{userId}")
    @PreAuthorize("hasAuthority('view_movie')")
    ResponseEntity<?> readMovieByUserController(@PathVariable("userId")Long userId){
        List<MovieDto> movies=this.movieService.getAllMovieByUser(userId);
        if(movies.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!"),HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(movies,HttpStatusCode.valueOf(200));

    }
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('manage_movie')")
    ResponseEntity<?> updateMovieController(@RequestBody MovieDto movieDto,Principal principal) throws Exception {
        String message=this.movieService.updateMovie(movieDto,principal);
        if(message.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("Something went wrong"),HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(new ApiResponse(message),HttpStatusCode.valueOf(200));
    }

}
