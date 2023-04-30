package com.movie.recommendation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "rating_table")
public class Rating{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rating_id")
    private Long ratingId;
    @Column(name = "rating_number")
    private float ratingNumber;
    @Column(name = "rating_post_date")
    @JsonFormat(pattern = "yyyy/mm/dd")
    private Date ratingPostDate;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_fk",referencedColumnName = "user_id")
    @JsonBackReference(value = "user_table")
    private User user;


    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="movie_id_fk",referencedColumnName = "movie_id")
    @JsonBackReference(value = "movie_table")
    private Movie movie;





}
