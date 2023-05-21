package com.movie.recommendation.service;

import com.movie.recommendation.dto.RatingDto;
import com.movie.recommendation.model.Rating;

import java.awt.datatransfer.FlavorEvent;
import java.security.Principal;
import java.util.List;

public interface RatingService {
    RatingDto createRating(RatingDto ratingDto, Principal principal) throws Exception;
    List<Rating> getRatingByMovie(Long movieId);

    Float calculateAverageRatingForMovie(Long movieId);


}
