package com.movie.recommendation.controller;

import com.movie.recommendation.dto.RatingDto;
import com.movie.recommendation.helper.ApiResponse;
import com.movie.recommendation.model.Rating;
import com.movie.recommendation.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @PostMapping("/create")
    ResponseEntity<?> createRatingController(@RequestBody RatingDto ratingDto, Principal principal) throws Exception {
        RatingDto rating1=ratingService.createRating(ratingDto,principal);
        if(rating1.equals(null)){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!"), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(rating1,HttpStatusCode.valueOf(200));
    }
@GetMapping("/read/{movieId}")
 ResponseEntity<?> getRatingByMovie(@PathVariable Long movieId){
        List<Rating> ratings=ratingService.getRatingByMovie(movieId);
        if(ratings.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("there is no rating for the given movie!!!"),HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(ratings,HttpStatusCode.valueOf(200));
    }
}
