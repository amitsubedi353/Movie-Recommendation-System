package com.movie.recommendation.controller;


import com.movie.recommendation.dto.GenreDto;
import com.movie.recommendation.helper.ApiResponse;

import com.movie.recommendation.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('manage_genre')")
    ResponseEntity<?> createGenreController(@RequestBody GenreDto genreDto, Principal principal) throws Exception {
        GenreDto retrievedGenreDto=this.genreService.createGenre(genreDto,principal);
        if(retrievedGenreDto!=null) {
            return new ResponseEntity<>(retrievedGenreDto, HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(new ApiResponse("Something went wrong!!!"),HttpStatusCode.valueOf(500));

    }



    @GetMapping("read/{userId}")
    @PreAuthorize("hasAuthority('view_genre_admin')")
    ResponseEntity<?> getGenreByUserController(@PathVariable Long userId){
        List<GenreDto> genres=this.genreService.getGenreByUser(userId);
        if(genres==null){
            return new ResponseEntity<>(new ApiResponse("No genre for particular user"),HttpStatusCode.valueOf(200));
        }
        else {
            return new ResponseEntity<>(genres,HttpStatusCode.valueOf(200));
        }
    }
    @GetMapping("/readAll")
    @PreAuthorize("hasAnyAuthority({'view_genre','view_genre_admin'})")
    ResponseEntity<?> getAllGenreController(){
        List<GenreDto> genreDtos=genreService.getAllGenre();
        if(genreDtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("No genre found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(genreDtos);

    }


    @DeleteMapping("/delete/{genreId}")
    @PreAuthorize("hasAuthority('manage_genre')")
    ResponseEntity<ApiResponse> deleteGenreController(@PathVariable Long genreId,Principal principal) throws Exception {
        String message=this.genreService.deleteGenre(genreId,principal);
        if(message.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!"),HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(new ApiResponse(message),HttpStatusCode.valueOf(200));

    }


    @PutMapping("/update")
    @PreAuthorize("hasAuthority('manage_genre')")
    ResponseEntity<?> updateGenreController(@RequestBody GenreDto genreDto) throws Exception {
        String message=this.genreService.updateGenre(genreDto);
        if(message.isEmpty()){
            return new  ResponseEntity<>(new ApiResponse("Something went wrong"),HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(new ApiResponse(message),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/read")
    @PreAuthorize("hasAuthority('view_genre')")
    ResponseEntity<?> getGenreByNameController(@RequestParam("genreName")String genreName) throws Exception {
        GenreDto resultGenre =this.genreService.getGenreByName(genreName);
        if(resultGenre==null){
            return new ResponseEntity<>(new ApiResponse("there is no genre with the given genre name"),HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(resultGenre,HttpStatusCode.valueOf(200));
    }
}
