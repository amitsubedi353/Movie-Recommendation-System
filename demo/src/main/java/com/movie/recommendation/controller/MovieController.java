package com.movie.recommendation.controller;


import com.movie.recommendation.helper.ApiResponse;
import com.movie.recommendation.model.Movie;
import com.movie.recommendation.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/user/{userId}/genre/{genreId}/create")
    ResponseEntity<?> createMovieController(@PathVariable("userId")Long userId, @PathVariable("genreId")Long genreId, @RequestBody Movie movie) throws Exception {
        Movie movie1=this.movieService.createMovie(userId,genreId,movie);
        if(movie1==null){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!"), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(movie1,HttpStatusCode.valueOf(200));

    }

    @DeleteMapping("/user/{userId}/genre/{genreId}/delete")
    ResponseEntity<ApiResponse> deleteMovieController(@PathVariable("userId")Long userId,@PathVariable("genreId")Long genreId,@RequestParam("movieId")Long movieId){
        String message=this.movieService.deleteMovie(userId, genreId, movieId);
        if(message.equals("")){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!"),HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(new ApiResponse(message),HttpStatusCode.valueOf(200));

    }


@GetMapping("/user/{userId}/genre/{genreId}/read")
    ResponseEntity<?> readMovieByGenreController(@PathVariable("userId")Long userId,@PathVariable("genreId")Long genreId){
        List<Movie> movies=this.movieService.getAllMovieByGenre(userId, genreId);
        if(movies==null){
            return new ResponseEntity<>(new ApiResponse("Something went wrong"),HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(movies,HttpStatusCode.valueOf(200));

    }

    @GetMapping("/user/{userId}/read")
    ResponseEntity<?> readMovieByUserController(@PathVariable("userId")Long userId){
        List<Movie> movies=this.movieService.getAllMovieByUser(userId);
        if(movies==null){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!"),HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(movies,HttpStatusCode.valueOf(200));

    }


    @PutMapping("/user/{userId}/genre/{genreId}/update")
    ResponseEntity<?> updateMovieController(@PathVariable("userId")Long userId,@PathVariable("genreId")Long genreId,@RequestParam("movieId")Long movieId,@RequestBody Movie movie) throws Exception {
        Movie resultMovie=this.movieService.updateMovie(userId,genreId,movieId,movie);
        if(resultMovie==null){
            return new ResponseEntity<>(new ApiResponse("Something went wrong"),HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(resultMovie,HttpStatusCode.valueOf(200));

    }

}
