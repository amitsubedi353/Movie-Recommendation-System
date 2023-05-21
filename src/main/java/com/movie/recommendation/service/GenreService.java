package com.movie.recommendation.service;

import com.movie.recommendation.dto.GenreDto;
import com.movie.recommendation.model.Genre;
import com.movie.recommendation.model.Role;

import java.security.Principal;
import java.util.List;

public interface GenreService {

    GenreDto createGenre(GenreDto genreDto, Principal principal) throws Exception;

    List<GenreDto> getGenreByUser(Long userId);

    String updateGenre(GenreDto genreDto) throws Exception;

    String deleteGenre(Long genreId,Principal principal) throws Exception;

    GenreDto getGenreByName(String name) throws Exception;



}
