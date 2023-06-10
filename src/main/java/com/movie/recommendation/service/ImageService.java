package com.movie.recommendation.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface ImageService {
    Map<String,String> uploadImage(MultipartFile multipartFile, String path) throws IOException;
    InputStream serveImage(Long movieId) throws FileNotFoundException;
}
