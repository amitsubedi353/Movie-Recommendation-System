package com.movie.recommendation.controller;

import com.movie.recommendation.dto.RatingDto;
import com.movie.recommendation.helper.ApiResponse;
import com.movie.recommendation.model.Rating;
import com.movie.recommendation.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('create_rating')")
    ResponseEntity<Map<Integer,String>> createRatingController(@RequestBody RatingDto ratingDto, Principal principal) throws Exception {
        Map<Integer,String> rating1=ratingService.createRating(ratingDto,principal);
        if(rating1.containsKey(500)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(rating1);
        }
        return ResponseEntity.status(HttpStatus.OK).body(rating1);
    }
@GetMapping("/read/{movieId}")
@PreAuthorize("hasAuthority('view_rating')")
 ResponseEntity<?> getAverageRatingByMovie(@PathVariable Long movieId){
        List<Rating> ratings=ratingService.getRatingByMovie(movieId);
        if(ratings.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("there is no rating for the given movie!!!"),HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(ratings,HttpStatusCode.valueOf(200));
    }
}
