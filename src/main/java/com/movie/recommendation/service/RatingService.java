package com.movie.recommendation.service;

import com.movie.recommendation.dto.RatingDto;
import com.movie.recommendation.model.Rating;

import java.awt.datatransfer.FlavorEvent;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface RatingService {
    Map<Integer,String> createRating(RatingDto ratingDto, Principal principal) throws Exception;
}
