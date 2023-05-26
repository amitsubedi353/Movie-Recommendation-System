package com.movie.recommendation.helper;

import com.movie.recommendation.model.Movie;
import com.movie.recommendation.repo.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class AverageRatingService {
    @Autowired
    private  RatingRepo ratingRepo;
    @Autowired
    private QueryClass queryClass;

    public float calculateAverageRating(Long movieId){
        Movie retrievedMovie=queryClass.getMovieById(movieId);
        return ratingRepo.calculateAverageRatingForMovie(movieId);
    }

}
