package com.movie.recommendation.service.impl;


import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.*;
import com.movie.recommendation.repo.GenreRepository;
import com.movie.recommendation.repo.MovieRepository;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.repo.UserRepository;
import com.movie.recommendation.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private QueryClass queryClass;

    @Override
    public Movie createMovie(Long userId, Long genreId, Movie movie) throws Exception {
        User retrievedUser = queryClass.getUserById(userId);
        Genre retrievedGenre = queryClass.getgenreById(genreId);
        Movie retrievedMovie = this.movieRepository.findByMovieTitle(movie.getMovieTitle());
        if (retrievedMovie != null) {
            throw new Exception("movie with the given name already exists!!!");
        } else {

            List<userRole> userRoles = retrievedUser.getUserRoles();
            for (userRole eachUserRole : userRoles
            ) {
                Role role = this.roleRepository.findByRoleName(eachUserRole.getRole().getRoleName());
                if (role.getRoleName().equalsIgnoreCase("admin")) {
                    List<MovieGenre> movieGenreList = new ArrayList<>();
                    MovieGenre movieGenre = new MovieGenre();
                    movieGenre.setGenre(retrievedGenre);
                    movieGenre.setMovie(movie);
                    movie.setUser(retrievedUser);
                    movieGenreList.add(movieGenre);
                    movie.setMovieGenre(movieGenreList);
                    retrievedMovie = this.movieRepository.save(movie);

                }

            }

        }
        return retrievedMovie;
    }

    @Override
    public String deleteMovie(Long userId, Long genreId, Long movieId) {
        String message = "";

        User retrievedUser = queryClass.getUserById(userId);
        Genre retrievedGenre = queryClass.getgenreById(genreId);
        Movie retrievedMovie = queryClass.getMovieById(movieId);
        List<userRole> userRoles = retrievedUser.getUserRoles();
        for (userRole eachUserRole : userRoles
        ) {
            Role role = this.roleRepository.findByRoleName(eachUserRole.getRole().getRoleName());
            if (role.getRoleName().equalsIgnoreCase("admin")) {
                List<Genre> genres=retrievedUser.getGenreList();
                for (Genre eachGenre:genres
                     ) {
                   if( eachGenre.getGenreId().equals(retrievedGenre.getGenreId()))
                    {


                List<MovieGenre> movieGenreList = retrievedMovie.getMovieGenre();
                for (MovieGenre eachMovieGenre : movieGenreList
                ) {
                    eachMovieGenre.setGenre(null);
                    eachMovieGenre.setMovie(null);

                }
                retrievedMovie.setUser(null);

                this.movieRepository.delete(retrievedMovie);
                message = "movie deleted successfully!!!";
            }
                }

        }
        }

        return message;
    }

    @Override
    public List<Movie> getAllMovieByGenre(Long userId, Long genreId) {
        List<Movie> movies = new ArrayList<>();
        User retrievedUser = queryClass.getUserById(userId);
        Genre genre = queryClass.getgenreById(genreId);
        List<userRole> userRoles = retrievedUser.getUserRoles();
        for (userRole eachUserRole : userRoles
        ) {
            Role role = this.roleRepository.findByRoleName(eachUserRole.getRole().getRoleName());
            if (role.getRoleName().equalsIgnoreCase("admin") || role.getRoleName().equalsIgnoreCase("normal")) {
                movies = this.movieRepository.findByMovieGenre(genre);
            }

        }
        return movies;
    }

    @Override
    public List<Movie> getAllMovieByUser(Long userId) {
        List<Movie> movies = new ArrayList<>();
        User retrievedUser = queryClass.getUserById(userId);
        List<userRole> userRoles = retrievedUser.getUserRoles();
        for (userRole eachUserRole : userRoles
        ) {
            Role role = this.roleRepository.findByRoleName(eachUserRole.getRole().getRoleName());
            if (role.getRoleName().equalsIgnoreCase("admin") || role.getRoleName().equalsIgnoreCase("normal")) {
                movies = this.movieRepository.findByUser(retrievedUser);
            }

        }

        return movies;
    }

    @Override
    public Movie updateMovie(Long userId, Long genreId, Long movieId, Movie movie) throws Exception {
        Movie movie1=new Movie();
        User retrievedUser = queryClass.getUserById(userId);
        Genre retrievedGenre = queryClass.getgenreById(genreId);
        Movie retrievedMovie = queryClass.getMovieById(movieId);
        List<MovieGenre> genres = retrievedMovie.getMovieGenre();
        for (MovieGenre eachMovieGenre : genres
        ) {
            if (eachMovieGenre.getGenre() == retrievedGenre) {


                List<userRole> userRoles = retrievedUser.getUserRoles();
                for (userRole eachUserRole : userRoles
                ) {
                    Role role = this.roleRepository.findByRoleName(eachUserRole.getRole().getRoleName());
                    if (role.getRoleName().equalsIgnoreCase("admin")) {
                        retrievedMovie.setMovieTitle(movie.getMovieTitle());
                        retrievedMovie.setAvgRating(movie.getAvgRating());
                        retrievedMovie.setReleaseDate(movie.getReleaseDate());
                        movie1=this.movieRepository.save(retrievedMovie);

                    }

                }
            }
        }


            return movie1;
        }

}
