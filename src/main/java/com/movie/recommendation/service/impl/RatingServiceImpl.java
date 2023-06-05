package com.movie.recommendation.service.impl;

import com.movie.recommendation.constant.AppConstant;
import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.dto.RatingDto;
import com.movie.recommendation.helper.AverageRatingService;
import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.*;
import com.movie.recommendation.repo.MovieRepository;
import com.movie.recommendation.repo.RatingRepo;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.repo.UserRepository;
import com.movie.recommendation.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private QueryClass queryClass;
    @Autowired
    private RatingRepo ratingRepo;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Map<Integer,String> createRating(RatingDto ratingDto, Principal principal) {
        Map<Integer, String> message = new HashMap<>();
        User loggedInUser = userRepository.findByUserEmail(principal.getName());
        Movie retrievedMovie = queryClass.getMovieById(ratingDto.getMovieId());
        if (loggedInUser.getUserId().equals(ratingDto.getUserId())) {
            Rating retrievedRating = ratingRepo.getRatingByUserAndMovie(loggedInUser.getUserId(),retrievedMovie.getMovieId());
            if (retrievedRating == null) {
                if (ratingDto.getRatingNumber()>=AppConstant.minRating&&ratingDto.getRatingNumber() <= AppConstant.maxRating) {
                    Rating rating = getRating(ratingDto);
                    rating.setMovie(retrievedMovie);
                    rating.setUser(loggedInUser);
                    rating.setRatingPostDate(LocalDateTime.now());
                    ratingRepo.save(rating);
                    message.put(200, "Rating created successfully!!!");
                    return message;
                } else {
                    message.put(500, "please select a appropriate rating number");
                    return message;
                }
            } else {
                message.put(500, "user has already created the rating for the given movie");
                return message;
            }
        }else {
            message.put(500,"Invalid user token!!!!");
        }
            return message;

    }

    private Rating getRating(RatingDto ratingDto){
        Rating rating=new Rating();
        rating.setRatingNumber(ratingDto.getRatingNumber());
        return rating;
    }




}