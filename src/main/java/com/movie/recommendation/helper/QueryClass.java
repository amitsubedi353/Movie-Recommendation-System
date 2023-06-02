package com.movie.recommendation.helper;

import com.movie.recommendation.Exception.ResourcenotFoundException;
import com.movie.recommendation.model.*;
import com.movie.recommendation.repo.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class QueryClass {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RatingRepo ratingRepo;
    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleById(Long id){
        return  this.roleRepository.findById(id).orElseThrow(()->new ResourcenotFoundException("role","roleId ",id));
    }

    public Genre getgenreById(Long id){
        return  this.genreRepository.findById(id).orElseThrow(()->new ResourcenotFoundException("genre","genreId ",id));
    }

    public User getUserById(Long id){
        return this.userRepository.findById(id).orElseThrow(()->new ResourcenotFoundException("user","userId ",id));
    }
    public Movie getMovieById(Long id){
        return this.movieRepository.findById(id).orElseThrow(()->new ResourcenotFoundException("movie","movieId ",id));
    }
    public Rating getRatingById(Long id){
        return this.ratingRepo.findById(id).orElseThrow(()->new ResourcenotFoundException("rating ","ratingId",id));
    }

}
