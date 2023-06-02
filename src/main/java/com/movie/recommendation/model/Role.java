package com.movie.recommendation.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import java.util.List;
import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="role_table")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="role_id")
    private long roleId;
    @Column(name="role_name")
    private String roleName;

    @OneToMany(cascade = CascadeType.ALL,fetch =FetchType.LAZY,mappedBy = "role")
    @JsonManagedReference(value = "role_table")
    private List<User> users;
    @OneToMany(cascade = CascadeType.ALL,fetch =FetchType.LAZY,mappedBy = "role")
    @JsonManagedReference(value = "role_table")
    private List<Authority> authorities;


}