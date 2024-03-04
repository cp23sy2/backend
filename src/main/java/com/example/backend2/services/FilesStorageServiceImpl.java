package com.example.backend2.services;

import com.example.backend2.Repository.CourseFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl implements FilesStorageService{
    private final Path root = Paths.get("uploads");

    @Autowired
    private CourseFileRepository courseFileRepository;

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {

        }
    }

    @Override
    public void save(MultipartFile file, String datetime) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(datetime + file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteFileAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public Resource deleteFile(String filename) {
        try {
            System.out.println(filename);
            FileSystemUtils.deleteRecursively(root.resolve(filename));
        } catch (IOException e) {
            throw new RuntimeException("Could not delete the files please check name file");
        }
        return null;
    }

//    @Override
//    public boolean deleteFile(String filename) {
//        try {
//            return FileSystemUtils.deleteRecursively(root.resolve(filename));
//        } catch (IOException e) {
//            throw new RuntimeException("Could not delete the files please check name file");
//        }
//    }
}
