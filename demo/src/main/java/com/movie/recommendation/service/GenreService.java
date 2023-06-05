package com.movie.recommendation.service;

import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;
import com.movie.recommendation.model.userRole;

import java.util.List;

public interface GenreService {

    Genre createGenre(Long userId,Genre genre,List<Role> roles) throws Exception;

    List<Genre> getGenreByUser(Long userId,List<Role> roles);

    Genre updateGenre(Long userId,Long genreId,Genre genre,List<Role> roles) throws Exception;

    String deleteGenre(Long userId,Long genreId,List<Role> roles) throws Exception;



    Genre getGenreByName(Long userId,String genreName,List<Role> roleList) throws Exception;



}
