package com.movie.recommendation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="user_table")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Long userId;
    @Column(name="user_name")
    private String userName;
    @Column(name="user_email",unique = true,nullable = false)
    private String userEmail;
    @Column(name="user_password")
    private String userPassword;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
            @JsonManagedReference(value = "user_table")
    List<userRole> userRoles;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    @JsonManagedReference(value = "user_table")
    private List<Genre> genreList;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    @JsonManagedReference(value = "user_table")
    private List<Movie> movieList;


    @OneToMany(cascade =  CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    @JsonManagedReference(value = "user_table")
    private List<Rating> ratings;





}
