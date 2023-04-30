package com.movie.recommendation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="movie_table")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="movie_id")
    private Long movieId;

    @Column(name = "movie_title",unique = true)
    private String movieTitle;

    @Column(name="release_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    @Column(name="avg_rating")
    private float avgRating;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="genre_id_fk",referencedColumnName = "genre_id")
    @JsonBackReference(value = "genre_table")
    private Genre genre;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_fk",referencedColumnName ="user_id")
    @JsonBackReference(value = "user_table")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "movie")
    @JsonManagedReference(value = "movie_table")
    private List<Rating> ratings;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="watchlist_id_fk",referencedColumnName = "watchlist_id")
    @JsonBackReference(value = "watchlist_table")
    private WatchList watchlist;






}
