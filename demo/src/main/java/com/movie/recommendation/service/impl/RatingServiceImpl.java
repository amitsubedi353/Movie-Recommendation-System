package com.movie.recommendation.service.impl;

import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.*;
import com.movie.recommendation.repo.RatingRepo;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private QueryClass queryClass;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RatingRepo ratingRepo;
    @Override
    public Rating createRating(Long userId, Long movieId, Rating rating) throws Exception {
        User user=this.queryClass.getUserById(userId);
        Movie movie =this.queryClass.getMovieById(movieId);
        Rating rating1=new Rating();

            List<Rating> ratings=movie.getRatings();
            for (Rating eachRating:ratings
                 ) {
                if(eachRating.getUser().getUserId().equals(user.getUserId())){
                  throw new Exception("Rating for the given user already exist for the movie");
                }
                else {
                    List<userRole> userRoles = user.getUserRoles();
                    if (userRoles.isEmpty()) {
                        throw new Exception("Provided role doesn't match with the existing role in db");
                    } else {
                        for (userRole eachUserRole : userRoles
                        ) {
                            Role role = this.roleRepository.findByRoleName(eachUserRole.getRole().getRoleName());

                            if (role.getRoleName().equalsIgnoreCase("admin") || role.getRoleName().equalsIgnoreCase("normal")) {
                                Date date = new Date();
                                int num = rating.getRatingPostDate().compareTo(date);
                                if (num > 0) {
                                    rating.setUser(user);
                                    rating.setMovie(movie);

                                    rating1 = this.ratingRepo.save(rating);
                                } else {
                                    throw new Exception("Invalid date");
                                }
                            }


                        }
                    }
                }
                }



        return rating1;
    }

    @Override
    public Rating deleteRating(Long userId, Long movieId, Long ratingId) {
        return null;
    }

    @Override
    public List<Rating> getRatingByMovie(Long movieId) {
        return null;
    }

    @Override
    public Rating getRatingById(Long ratingId) {
        return null;
    }
}
