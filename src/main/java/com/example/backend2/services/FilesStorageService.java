package com.example.backend2.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {
    public void init();

    public void save(MultipartFile file, String dateTime);

    public Resource load(String filename);

    public void deleteFileAll();

    public Stream<Path> loadAll();

//    public boolean delete(String filename);

    public Resource deleteFile(String filename);
}
