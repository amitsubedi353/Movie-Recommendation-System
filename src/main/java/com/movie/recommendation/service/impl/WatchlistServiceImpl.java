package com.movie.recommendation.service.impl;

import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.Movie;
import com.movie.recommendation.model.User;
import com.movie.recommendation.model.WatchList;
import com.movie.recommendation.repo.UserRepository;
import com.movie.recommendation.repo.WatchlistRepo;
import com.movie.recommendation.service.WatchlistService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Transactional
@Service
public class WatchlistServiceImpl implements WatchlistService {
    @Autowired
    private WatchlistRepo watchlistRepo;
    @Autowired
    private QueryClass queryClass;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Map<Integer, String> addMovieToWatchList(Long movieId, Principal principal) {
        Map<Integer,String> message=new HashMap<>();
        Movie movie=queryClass.getMovieById(movieId);
        User loggedInUser=userRepository.findByUserEmail(principal.getName());
        WatchList watchList=watchlistRepo.findWatchlistByUserAndMovie(loggedInUser.getUserId(),movieId);
        if(watchList!=null){
            message.put(500,"user already has watchlist for the given movie");
            return message;
        }
        watchList.setMovie(movie);
        watchList.setUser(loggedInUser);
        watchlistRepo.save(watchList);
        message.put(200,"movie added to the watchlist successfully!!!");
        return message;
    }

    @Override
    public Map<Integer, String> removeMovieFromWatchList(Long movieId, Principal principal) {
        Map<Integer,String> message=new HashMap<>();
        Movie retrievedMovie=queryClass.getMovieById(movieId);
        User loggedInUser=userRepository.findByUserEmail(principal.getName());
        WatchList watchList=watchlistRepo.findWatchlistByUserAndMovie(loggedInUser.getUserId(),movieId);
        if(watchList==null){
            message.put(500,"LoggedInuser does not save the given movie to the watchlist");
            return message;
        }
        watchList.setUser(null);
        watchList.setMovie(null);
        watchlistRepo.delete(watchList);
        message.put(200,"Given movie removed from the watchlist successfully!!!");
        return message;
    }

    @Override
    public List<MovieDto> viewAllMovieInWatchListByUser(Principal principal) {
        User loggedInUser=userRepository.findByUserEmail(principal.getName());


        return null;
    }
}
