package com.movie.recommendation.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.helper.ApiResponse;
import com.movie.recommendation.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('manage_movie')")
    ResponseEntity<?> createMovieController(@RequestParam("file")MultipartFile file,@RequestParam("movieDto")String movieDto,Principal principal) throws Exception {
        MovieDto movieDto1=getMovie(movieDto);
        Map<String,String> message=movieService.createMovie(file,movieDto1,principal);
        if(message.containsKey(500)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);

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


@GetMapping("/readByGenre")
@PreAuthorize("hasAuthority('view_movie')")
    ResponseEntity<?> readMovieByGenreController(@RequestParam("genre")String genre){
        List<MovieDto> movies=this.movieService.getAllMovieByGenre(genre);
        if(movies.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("No movie for the given genre"),HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(movies,HttpStatusCode.valueOf(200));

    }

    @GetMapping("/read-all-movie")
    @PreAuthorize("hasAuthority('view_movie')")
    ResponseEntity<?> readMovieByUserController(){
        List<MovieDto> movies=this.movieService.getAllMovie();
        if(movies.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!"),HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(movies,HttpStatusCode.valueOf(200));

    }
    @GetMapping("/read/{movieId}")
    @PreAuthorize("hasAuthority('view_movie')")
    ResponseEntity<MovieDto> readMovieController(@PathVariable Long movieId){
        MovieDto movieDto=this.movieService.getMovieById(movieId);
        return ResponseEntity.status(HttpStatus.OK).body(movieDto);

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

    private MovieDto getMovie(String movieDto) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(movieDto,MovieDto.class);
    }

}
