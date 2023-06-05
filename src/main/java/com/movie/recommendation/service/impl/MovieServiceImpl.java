package com.movie.recommendation.service.impl;


import com.movie.recommendation.dto.MovieDto;
import com.movie.recommendation.helper.AverageRatingService;
import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.*;
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
import java.lang.reflect.Array;
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
    public Map<String,String> createMovie(MultipartFile multipartFile, MovieDto movieDto,Principal principal) throws IOException {
        User retrievedUser=userRepository.findByUserEmail(principal.getName());
       Map<String,String> message=new HashMap<>();
       Map<String,String> resultMessage=new HashMap<>();
           Movie movie=getMovie(movieDto);
           if(movie==null){
                resultMessage.put("500","provide a valid genre type!!!");
                return resultMessage;
           }
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
           movieRepository.save(movie);
           resultMessage.put("200:","movie created successfully");
           resultMessage.put("imagePath:",movie.getFullPath());
       return resultMessage;
    }

    @Override
    public String deleteMovie(Long movieId, Principal principal) {
        String message = "";
        Movie retrievedMovie=queryClass.getMovieById(movieId);
        User loggedInUser=userRepository.findByUserEmail(principal.getName());
        if(loggedInUser.getUserId().equals(retrievedMovie.getUser().getUserId())){
            retrievedMovie.setUser(null);
            //retrievedMovie.setGenre(null);
            movieRepository.delete(retrievedMovie);
            message="movie deleted successfully!!!";
        }else{
            return null;
        }


        return message;
    }

    @Override
    public List<MovieDto> getAllMovieByGenre(String genre) {
        List<MovieDto> movieDtos=new ArrayList<>();
        List<Movie> movies=movieRepository.findByMovieGenre(genre);
        if(movies.isEmpty()){
            return null;
        }
        for (Movie eachMovie:movies
             ) {
            movieDtos.add(getMovieDto(eachMovie));

        }
        return movieDtos;
    }

    @Override
    public List<MovieDto> getAllMovie() {
        return getMovieDto(movieRepository.findAll());
    }


    public List<MovieDto> getMovieDto(List<Movie> movies) {
        List<MovieDto>movieDtos=new ArrayList<>();
        if(movies.isEmpty()){
            return null;
        }
        for (Movie eachMovie:movies
             ) {
            MovieDto movieDto=new MovieDto();
            if(eachMovie.getRatings().size()==1){
                movieDto.setMovieDescription(eachMovie.getMovieDescription());
                movieDto.setMovieTitle(eachMovie.getMovieTitle());
                movieDto.setReleaseDate(eachMovie.getReleaseDate());
                movieDto.setGenreType(eachMovie.getMovieGenre());
                movieDto.setImageFullPath(eachMovie.getFullPath());
                movieDto.setAvgRating(calculateAverageRatingForSingleMovie(eachMovie));
                movieDtos.add(movieDto);
                continue;
            }else if(eachMovie.getRatings().size()==0){
                movieDto.setMovieTitle(eachMovie.getMovieTitle());
                movieDto.setMovieDescription(eachMovie.getMovieDescription());
                movieDto.setReleaseDate(eachMovie.getReleaseDate());
                movieDto.setImageFullPath(eachMovie.getFullPath());
                movieDto.setGenreType(eachMovie.getMovieGenre());
                movieDtos.add(movieDto);
                continue;
            }else {
                movieDto = getMovieDto(eachMovie);
                movieDtos.add(movieDto);
            }

        }
        return movieDtos;
    }



    @Override
    public MovieDto getMovieById(Long id) {
        Movie movie=queryClass.getMovieById(id);
        List<MovieDto> movieDtos=getMovieDto(List.of(movie));
        for (MovieDto eachMovieDto:movieDtos
             ) {
            return eachMovieDto;
        }
        return null;
    }

    @Override
    public String updateMovie(MovieDto movieDto,Principal principal) throws Exception {
       /* String message="";
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
        }*/
        return null;
    }


    private Movie getMovie(MovieDto movieDto){
        Movie movie=new Movie();
        movie.setMovieTitle(movieDto.getMovieTitle());
        movie.setMovieDescription(movieDto.getMovieDescription());
        String genre=getGenreType(movieDto.getGenreType());
        if(genre.contains("valid")){
            return null;
        }
        movie.setMovieGenre(genre);
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
        movieDto.setAvgRating(ratingService.calculateAverageRating(movie.getMovieId()));
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setImageFullPath(movie.getFullPath());
        movieDto.setGenreType(movie.getMovieGenre());
        return movieDto;
    }

    private String getGenreType(String genreType){
        String result="";
        switch (genreType){
            case "Romance":
                result=GenreType.Romance.toString();
                break;
            case "Thriller":
                result=GenreType.Thriller.toString();
                break;
            case "Horror":
                result=GenreType.Horror.toString();
                break;
            case "Adventure":
                result=GenreType.Adventure.toString();
                break;
            case "Comedy":
                result=GenreType.Comedy.toString();
                break;
            default:
                result="please provide a valid genre";
                break;

        }
        return result;
    }

    private float calculateAverageRatingForSingleMovie(Movie movie){
        float rating=0;
         List<Rating> ratings=movie.getRatings();
        for (Rating eachRating:ratings
             ) {
            rating=eachRating.getRatingNumber();
        }
        return rating;
    }

}