package com.movie.recommendation.dto;
import com.movie.recommendation.model.Genre;
import com.movie.recommendation.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieDto {
    private Long movieId;
    private String movieTitle;
    private String movieDescription;
    private Date releaseDate;
    private float avgRating;
    private Long genreId;
    private Long userId;

}
