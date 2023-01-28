package com.movie.recommendation.service;

import com.movie.recommendation.model.Rating;

import java.util.List;

public interface RatingService {
    Rating createRating(Long userId,Long movieId,Rating rating) throws Exception;
    Rating deleteRating(Long userId,Long movieId,Long ratingId);
    List<Rating> getRatingByMovie(Long movieId);
    Rating getRatingById(Long ratingId);


}
