package com.movie.recommendation.service.impl;

import com.movie.recommendation.constant.AppConstant;
import com.movie.recommendation.dto.RatingDto;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public RatingDto createRating(RatingDto ratingDto, Principal principal){
        User loggedInUser=userRepository.findByUserEmail(principal.getName());
        Movie retrievedMovie=queryClass.getMovieById(ratingDto.getMovieId());
        if(loggedInUser.getUserId().equals(ratingDto.getUserId())){
            Rating retrievedRating=ratingRepo.findByUserAndMovie(loggedInUser,retrievedMovie);
            if(retrievedRating==null){
                if(ratingDto.getRatingNumber()<= AppConstant.maxRating){
                    Rating rating=new Rating();
                    rating.setMovie(retrievedMovie);
                    rating.setUser(loggedInUser);
                    rating.setRatingNumber(ratingDto.getRatingNumber());
                    rating=ratingRepo.save(rating);
                    ratingDto.setRatingId(rating.getRatingId());
                    return ratingDto;

                }

            }else
            {
                return null;
            }

        }else {
            return null;
        }
        return null;
    }

    @Override
    public List<Rating> getRatingByMovie(Long movieId) {
        Movie retrievedMovie=queryClass.getMovieById(movieId);
        return ratingRepo.findByMovie(retrievedMovie);

    }

    @Override
    public Float calculateAverageRatingForMovie(Long movieId) {
        Movie retrievedMovie=queryClass.getMovieById(movieId);
        return null;
    }

}
