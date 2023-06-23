package com.tiembanhhoangtube.Service.impl;

import com.tiembanhhoangtube.Service.StogareService;
import com.tiembanhhoangtube.config.StogareProprties;
import com.tiembanhhoangtube.exeption.StogareFileNotFoundExeption;
import com.tiembanhhoangtube.exeption.StorageExeption;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStogareServideimpl implements StogareService {
    private final Path  rootLocation;

    @Override
    public String getStogaredFilename(MultipartFile file, String id){
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return "p" + id +"." + ext;
    }

    public FileSystemStogareServideimpl(StogareProprties properties){
        this.rootLocation = Paths.get(properties.getLocation());
    }
    @Override
    public void store(MultipartFile file, String storedFilename) throws StorageExeption {
        try {
            if(file.isEmpty()){
                throw new StorageExeption("Failed to store empty file");
            }

            Path destinationFile = this.rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();

            if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())){
                throw new StorageExeption("Cannot store file outside current directory");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (Exception e){
            System.out.println("Lỗi không thể lưu file : " + e.getMessage());
            throw new StorageExeption("Failed to store file",e);
        }
    }

    @Override
    public Resource loadAsResource(String filename){
        try {
            Path file = load(filename);
            System.out.println("file: " + file.toAbsolutePath());
            org.springframework.core.io.Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            throw new StogareFileNotFoundExeption("Could not read file: " + filename);
        }catch (Exception e){
            throw new StogareFileNotFoundExeption("Could not read file: " + filename);
        }
    }

    @Override
    public Path load(String filename){
        return this.rootLocation.resolve(filename);
    }

    @Override
    public void delete(String storedFilename) throws Exception{
        Path destinationFile = rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();
        Files.delete(destinationFile);
    }

    @Override
    public void init(){
        try {
            Files.createDirectories(rootLocation);
            System.out.println(rootLocation.toString());
        }catch (Exception e){
            throw new StorageExeption("Could not create directory: " + rootLocation);
        }
    }
}
