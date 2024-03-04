package com.example.backend2.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseFileAllDto {
    private Integer id;
    private String title;
    private String categoryName;
    private String courseName;
    private String courseFullName;
    private String emailOwner;
    private String fileDescription;
    private Instant fileCreatedOn;
    private String fileUpload;
    private Boolean hide;
}
