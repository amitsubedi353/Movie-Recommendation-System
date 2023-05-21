package com.movie.recommendation.service.impl;


import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.*;
import com.movie.recommendation.repo.GenreRepository;
import com.movie.recommendation.repo.MovieRepository;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.repo.UserRepository;
import com.movie.recommendation.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QueryClass queryClass;

    @Override
    public MovieDto createMovie(MovieDto movieDto,Principal principal)  {
        User retrievedUser1=userRepository.findByUserEmail(principal.getName());
        User retrievedUser=queryClass.getUserById(movieDto.getUserId());
        Genre retrievedGenre=queryClass.getgenreById(movieDto.getGenreId());
        if(retrievedGenre.getUser().getUserId().equals(retrievedUser.getUserId())&&retrievedUser1.getUserId().equals(retrievedUser.getUserId())){
            Movie  movie=new Movie();
            movie.setMovieTitle(movieDto.getMovieTitle());
            movie.setReleaseDate(movieDto.getReleaseDate());
            movie.setUser(retrievedUser);
            movie.setGenre(retrievedGenre);
            movie=movieRepository.save(movie);
            movieDto.setMovieId(movie.getMovieId());

        }
        return movieDto;

    }

    @Override
    public String deleteMovie(Long movieId, Principal principal) {
        String message = "";
        Movie retrievedMovie=queryClass.getMovieById(movieId);
        User loggedInUser=userRepository.findByUserEmail(principal.getName());
        if(loggedInUser.getUserId().equals(retrievedMovie.getUser().getUserId())){
            retrievedMovie.setUser(null);
            retrievedMovie.setGenre(null);
            movieRepository.delete(retrievedMovie);
            message="movie deleted successfully!!!";
        }else{
            return null;
        }


        return message;
    }

    @Override
    public List<MovieDto> getAllMovieByGenre(Long genreId) {

        Genre retrievedGenre=queryClass.getgenreById(genreId);

        List<Movie> movies=movieRepository.findByGenre(retrievedGenre);

        if(movies.isEmpty()){
            return null;
        }
        List<MovieDto> movieDtos=new ArrayList<>();

        for (Movie eachMovie:movies
        ) {
            MovieDto movieDto=this.modelMapper.map(eachMovie,MovieDto.class);
            movieDto.setGenreId(eachMovie.getGenre().getGenreId());
            movieDto.setUserId(eachMovie.getUser().getUserId());
            movieDtos.add(movieDto);
        }
        return movieDtos;
    }

    @Override
    public List<MovieDto> getAllMovieByUser(Long userId) {
        User retrievedUser=queryClass.getUserById(userId);
        List<Movie> movies=movieRepository.findByUser(retrievedUser);
        if(movies.isEmpty()){
            return null;
        }
        List<MovieDto> movieDtos=new ArrayList<>();

        for (Movie eachMovie:movies
        ) {
            MovieDto movieDto=this.modelMapper.map(eachMovie,MovieDto.class);
            movieDto.setGenreId(eachMovie.getGenre().getGenreId());
            movieDto.setUserId(eachMovie.getUser().getUserId());
            movieDtos.add(movieDto);
        }
        return movieDtos;
    }

    @Override
    public String updateMovie(MovieDto movieDto,Principal principal) throws Exception {
        String message="";
        Movie retrievedMovie=queryClass.getMovieById(movieDto.getMovieId());
        User retrievedUser=userRepository.findByUserEmail(principal.getName());
        if(retrievedUser.getUserId().equals(retrievedMovie.getUser().getUserId())){
            if(!movieDto.getGenreId().equals(retrievedMovie.getGenre().getGenreId())) {
                Optional<Genre> retrievedGenre = genreRepository.findById(movieDto.getGenreId());
                if(!retrievedGenre.isEmpty()) {
                    List<Genre> genres = retrievedUser.getGenreList();
                    for (Genre eachGenre : genres
                    ) {
                        if (eachGenre.getGenreId().equals(movieDto.getGenreId())) {
                            retrievedMovie.setGenre(retrievedGenre.get());
                        }

                    }
                }
            }
            retrievedMovie.setMovieTitle(movieDto.getMovieTitle());
            retrievedMovie.setReleaseDate(movieDto.getReleaseDate());
            movieRepository.save(retrievedMovie);
            message="movie updated succcessfully!!!";
        }else{
            return null;
        }
        return message;
    }

}