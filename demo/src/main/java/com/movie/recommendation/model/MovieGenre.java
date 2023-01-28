package com.movie.recommendation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="movie_genre_table")
public class MovieGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_genre_id")
    private Long movieGenreId;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id_fk",referencedColumnName = "genre_id")
    @JsonBackReference(value = "genre_table")
    private Genre genre;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="movie_id_fk",referencedColumnName = "movie_id")
    @JsonBackReference(value = "movie_table")
    private Movie movie;


}
