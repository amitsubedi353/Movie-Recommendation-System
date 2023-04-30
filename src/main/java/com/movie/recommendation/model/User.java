package com.movie.recommendation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


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

@ManyToOne(cascade =CascadeType.ALL,fetch = FetchType.LAZY)
@JoinColumn(name = "role_id_fk",referencedColumnName = "role_id")
@JsonBackReference(value = "role_table")
private Role role;


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
