package com.example.backend2;

import com.example.backend2.services.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class Backend2Application implements CommandLineRunner {

    @Resource
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(Backend2Application.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        storageService.init();
    }

}
