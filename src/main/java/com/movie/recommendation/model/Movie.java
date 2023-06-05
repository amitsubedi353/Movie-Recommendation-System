package com.movie.recommendation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="movie_table")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="movie_id")
    private Long movieId;

    @Column(name = "movie_title",unique = true)
    private String movieTitle;
    @Column(name = "movie_description")
    private String movieDescription;
    @Column(name = "movie_image")
    private String image;
    @Column(name = "movie_full_path")
    private String fullPath;
    @Column(name="movie_genre")
    private String movieGenre;
    @Column(name="release_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_fk",referencedColumnName ="user_id")
    @JsonBackReference(value = "user_table")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "movie")
    @JsonManagedReference(value = "movie_table")
    private List<Rating> ratings;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "movie")
    @JsonManagedReference(value = "movie_table")
    private List<WatchList> watchLists;

}
