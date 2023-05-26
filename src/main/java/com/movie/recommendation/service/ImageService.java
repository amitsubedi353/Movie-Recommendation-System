package com.movie.recommendation.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ImageService {
    Map<String,String> uploadImage(MultipartFile multipartFile, String path) throws IOException;
}
