package com.movie.recommendation.helper;

import com.movie.recommendation.Exception.ResourcenotFoundException;
import com.movie.recommendation.model.Movie;
import com.movie.recommendation.model.Rating;
import com.movie.recommendation.model.User;
import com.movie.recommendation.repo.MovieRepository;
import com.movie.recommendation.repo.RatingRepo;
import com.movie.recommendation.repo.UserRepository;
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
