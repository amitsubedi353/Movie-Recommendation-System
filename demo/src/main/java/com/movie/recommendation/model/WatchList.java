package com.movie.recommendation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="watchlist_table")
public class WatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "watchlist_id")
    private Long watchListId;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="user_id_fk",referencedColumnName = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "watchlist")
    @JsonManagedReference(value = "watchlist_table")
    private List<Movie> movies;



}
