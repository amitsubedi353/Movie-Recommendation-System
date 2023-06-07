package com.movie.recommendation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Getter
@Setter
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
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id_fk",referencedColumnName = "movie_id")
    @JsonBackReference(value ="movie_table")
    private Movie movie;



}
