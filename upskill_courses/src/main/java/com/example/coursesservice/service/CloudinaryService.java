package com.example.coursesservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
//    String uploadImage(MultipartFile file) throws IOException;

    CloudinaryImage upload(MultipartFile file) throws IOException;

    boolean delete(String publicId);
}
