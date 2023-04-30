package com.movie.recommendation.dto;

import com.movie.recommendation.model.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenreDto {
    private Long genreId;

    private String genreName;

    private String genreDescription;

    private String genreCreated;
    private User user;

}
