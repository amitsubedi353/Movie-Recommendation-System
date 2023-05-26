package com.movie.recommendation.service.impl;


import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.helper.AverageRatingService;
import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.*;
import com.movie.recommendation.repo.GenreRepository;
import com.movie.recommendation.repo.MovieRepository;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.repo.UserRepository;
import com.movie.recommendation.service.ImageService;
import com.movie.recommendation.service.MovieService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    @Value("${movie.path}")
    private String imagePath;
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
    @Autowired
    private ImageService imageService;
    @Autowired
    private AverageRatingService ratingService;


    @Transactional(rollbackOn = IOException.class)
    @Override
    public String createMovie(MultipartFile multipartFile, MovieDto movieDto,Principal principal) throws IOException {

        User retrievedUser=userRepository.findByUserEmail(principal.getName());
       Map<String,String> message=new HashMap<>();
       Genre genre=queryClass.getgenreById(movieDto.getGenreId());
       List<Genre> genres=retrievedUser.getGenreList();
       if(genres.contains(genre)){
           Movie movie=getMovie(movieDto);
           try{
               message=imageService.uploadImage(multipartFile,imagePath);
           }catch (IOException ioException){
               throw new IOException(ioException.getLocalizedMessage());
           }
           for (Map.Entry<String,String> entry:message.entrySet()){
               movie.setImage(entry.getKey());
               movie.setFullPath(entry.getValue());
           }
           movie.setUser(retrievedUser);
           movie.setGenre(genre);
           movieRepository.save(movie);
       }

        return "movie created successfully!!!";

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
        return movies.stream().map(movie -> getMovieDto(movie)).collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getAllMovie() {

        List<Movie> movies=movieRepository.findAll();
        if(movies.isEmpty()){
            return null;
        }
        return movies.stream().map(movie -> getMovieDto(movie)).collect(Collectors.toList());
    }

    @Override
    public MovieDto getMovieById(Long id) {
        Movie movie=queryClass.getMovieById(id);
        return getMovieDto(movie);
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


    private Movie getMovie(MovieDto movieDto){
        Movie movie=new Movie();
        movie.setMovieTitle(movieDto.getMovieTitle());
        movie.setMovieDescription(movieDto.getMovieDescription());
        int value=movieDto.getReleaseDate().compareTo(new Date());
        if(value<0){
            throw new RuntimeException("Invalid movie date!!!");
        }else {
            movie.setReleaseDate(movieDto.getReleaseDate());
        }
        return movie;
    }

    private MovieDto getMovieDto(Movie movie){
        MovieDto movieDto=new MovieDto();
        movieDto.setMovieTitle(movie.getMovieTitle());
        movieDto.setMovieDescription(movie.getMovieDescription());
        movieDto.setGenreId(movie.getGenre().getGenreId());
        movieDto.setAvgRating(ratingService.calculateAverageRating(movie.getMovieId()));
        movieDto.setReleaseDate(movie.getReleaseDate());
        return movieDto;
    }

}