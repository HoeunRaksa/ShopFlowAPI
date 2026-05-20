package com.flower.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String upload(MultipartFile file) throws IOException {

        File folder = new File(uploadDir);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        File dest = new File(folder, fileName);

        file.transferTo(dest);

        return "/uploads/images/" + fileName;
    }
}