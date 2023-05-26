package com.movie.recommendation.service.impl;

import com.movie.recommendation.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageUploadServiceImpl implements ImageService {
    @Override
    public Map<String, String> uploadImage(MultipartFile multipartFile, String path) throws IOException {
        Map<String,String> message=new HashMap<>();
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        String fileName= multipartFile.getOriginalFilename();
        String uniqueName= UUID.randomUUID().toString();
        String uniqueFileName=uniqueName.concat(fileName.substring(fileName.lastIndexOf(".")));
        String fullPath=path+File.pathSeparator+uniqueFileName;
        Files.copy(multipartFile.getInputStream(), Paths.get(fullPath));
        message.put(fileName,fullPath);
        return message;
    }
}
