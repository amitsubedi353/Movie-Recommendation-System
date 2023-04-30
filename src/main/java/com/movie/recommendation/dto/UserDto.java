package com.movie.recommendation.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long userId;
    private String userName;
    @Column(name="user_email",unique = true,nullable = false)
    private String userEmail;
    private String userPassword;
    private Long roleId;

}
