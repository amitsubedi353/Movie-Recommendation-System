package com.movie.recommendation.controller;

import com.movie.recommendation.helper.ApiResponse;
import com.movie.recommendation.model.Rating;
import com.movie.recommendation.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @PostMapping("/user/{userId}/movie/{movieId}/create")
    ResponseEntity<?> createRatingController(@PathVariable Long userId, @PathVariable Long movieId, @RequestBody Rating rating) throws Exception {
        Rating rating1=this.ratingService.createRating(userId,movieId,rating);
        if(rating1.equals(null)){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!"), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(rating1,HttpStatusCode.valueOf(200));
    }
}
