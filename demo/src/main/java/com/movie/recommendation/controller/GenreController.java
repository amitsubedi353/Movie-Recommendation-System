package com.movie.recommendation.controller;


import com.movie.recommendation.helper.ApiResponse;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.userRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

public class GenreController {
    @Autowired
    private GenreService genreService;


    @PostMapping("/user/{userId}/genre/create")
    ResponseEntity<?> createGenreController(@PathVariable("userId")Long userId, @RequestBody Genre genre) throws Exception {

        List<Role> roles=new ArrayList<>();
        Role role=new Role();
        role.setRoleName("ADMIN");
        roles.add(role);


        Genre retrievedGenre=this.genreService.createGenre(userId,genre,roles);
        return new ResponseEntity<>(retrievedGenre, HttpStatusCode.valueOf(200));

    }



    @GetMapping("/user/genre/getGenreByUser")
    ResponseEntity<?> getGenreByUserController(@RequestParam("userId")Long userId){
        List<Role> roles=new ArrayList<>();
        Role role=new Role();
        role.setRoleName("NORMAL");
        roles.add(role);


        List<Genre> genres=this.genreService.getGenreByUser(userId,roles);
        if(genres==null){
            return new ResponseEntity<>(new ApiResponse("No genre for particular user"),HttpStatusCode.valueOf(200));
        }
        else {
            return new ResponseEntity<>(genres,HttpStatusCode.valueOf(200));
        }
    }


    @DeleteMapping("/user/{userId}/genre/delete")
    ResponseEntity<ApiResponse> deleteGenreController(@PathVariable("userId")Long userId,@RequestParam("genreId")Long genreId) throws Exception {

        List<Role> roles=new ArrayList<>();
        Role role=new Role();
        role.setRoleName("ADMIN");
        roles.add(role);
                String message=this.genreService.deleteGenre(userId,genreId,roles);
                return new ResponseEntity<>(new ApiResponse(message),HttpStatusCode.valueOf(200));

    }


    @PutMapping("/user/{userId}/genre/update")
    ResponseEntity<?> updateGenreController(@PathVariable("userId")Long userId,@RequestParam("genreId")Long genreId,@RequestBody Genre genre) throws Exception {

        List<Role> roles=new ArrayList<>();
        Role role=new Role();
        role.setRoleName("ADMIN");
        roles.add(role);
        Genre updatedGenre=this.genreService.updateGenre(userId,genreId,genre,roles);
        if(updatedGenre==null){
            return new  ResponseEntity<>(new ApiResponse("User is not admin to update the genre"),HttpStatusCode.valueOf(204));
        }
        return new ResponseEntity<>(updatedGenre,HttpStatusCode.valueOf(200));
    }
    @GetMapping("/user/{userId}/genre/read")
    ResponseEntity<?> getGenreByNameController(@PathVariable("userId")Long userId,@RequestParam("genreName")String genreName) throws Exception {
        List<Role> roles=new ArrayList<>();
        Role role=new Role();
        role.setRoleName("ADMIN");
        roles.add(role);

       Genre resultGenre =this.genreService.getGenreByName(userId,genreName,roles);
       if(resultGenre==null){
           return new ResponseEntity<>(new ApiResponse("there is no genre with the given genre name"),HttpStatusCode.valueOf(200));
       }
       return new ResponseEntity<>(resultGenre,HttpStatusCode.valueOf(200));
    }



}
