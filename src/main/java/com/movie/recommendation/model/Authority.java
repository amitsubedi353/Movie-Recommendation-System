package com.movie.recommendation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
@Entity
@Table(name="authority_table")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String authority;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id_fk",referencedColumnName = "role_id")
    @JsonBackReference(value = "role_table")
    private Role role;

    public Authority(String authority){
        this.authority=authority;

    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}