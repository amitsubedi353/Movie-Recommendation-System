package com.movie.recommendation.controller;

import com.movie.recommendation.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/image")
public class ImageServeController {
    @Autowired
    private ImageService imageService;


    @GetMapping(value = "/{movieId}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveImage(@PathVariable Long movieId, HttpServletResponse response) throws IOException {
        InputStream inputStream=imageService.serveImage(movieId);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());

    }



}
