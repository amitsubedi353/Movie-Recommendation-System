package com.movie.recommendation.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="user_rating_movie_table")
public class UserRatingMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "movie_id")
    private Long movieId;
    @Column(name = "rating_id")
    private Long ratingId;
    @Column(name="rating_number")
    private float ratingNumber;
}
