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
import java.util.ArrayList;
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
    @Autowired
    private MovieServiceImpl movieService;


    @Override
    public Map<String,Object> addMovieToWatchList(Long movieId, Principal principal) {
        Map<String,Object> message=new HashMap<>();
        Movie movie=queryClass.getMovieById(movieId);
        User loggedInUser=userRepository.findByUserEmail(principal.getName());
        WatchList retrievedWatchList=watchlistRepo.findWatchlistByUserAndMovie(loggedInUser.getUserId(),movieId);
        WatchList watchList=new WatchList();
        if(retrievedWatchList!=null){
            message.put("status",500);
            message.put("data","user already has watchlist for the given movie");
            return message;
        }
        watchList.setMovie(movie);
        watchList.setUser(loggedInUser);
        watchlistRepo.save(watchList);
        message.put("status",200);
        message.put("data","movie added to the watchlist successfully!!!");
        return message;
    }

    @Override
    public Map<String,Object> removeMovieFromWatchList(Long movieId, Principal principal) {
        Map<String,Object> message=new HashMap<>();
        Movie retrievedMovie=queryClass.getMovieById(movieId);
        User loggedInUser=userRepository.findByUserEmail(principal.getName());
        WatchList watchList=watchlistRepo.findWatchlistByUserAndMovie(loggedInUser.getUserId(),movieId);
        if(watchList==null){
            message.put("status",500);
            message.put("data","Logged In user does not save the given movie to the watchlist");
            return message;
        }
        watchList.setUser(null);
        watchList.setMovie(null);
        watchlistRepo.delete(watchList);
        message.put("status",200);
        message.put("data","Given movie removed from the watchlist successfully!!!");
        return message;
    }

    @Override
    public Map<String,Object> viewAllMovieInWatchListByUser(Principal principal) {
        Map<String,Object> message=new HashMap<>();
        List<MovieDto> movieDtos=new ArrayList<>();
        User loggedInUser=userRepository.findByUserEmail(principal.getName());
        List<WatchList> watchLists=watchlistRepo.findWatchListForUser(loggedInUser.getUserId());
        List<Movie> movies=new ArrayList<>();
        if(watchLists.isEmpty()){
            message.put("status",200);
            message.put("data",null);
            return message;
        }
        for (WatchList eachWatchList:watchLists
             ) {
            Movie movie=eachWatchList.getMovie();
            movies.add(movie);
        }
        movieDtos=movieService.getMovieDto(movies);
        message.put("status",200);
        message.put("data",movieDtos);
        return message;
    }
}
