package com.tiembanhhoangtube.Service;


import com.tiembanhhoangtube.exeption.StorageExeption;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StogareService {
    String getStogaredFilename(MultipartFile file, String id);

    void store(MultipartFile file, String storedFilename) throws StorageExeption;

    Resource loadAsResource(String filename);

    Path load(String filename);

    void delete(String storedFilename) throws Exception;

    void init();
}
