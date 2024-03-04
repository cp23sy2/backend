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
public class CourseFileDetailDto {
    private Integer id;
    private String title;
    private String fileDescription;
    private Instant fileCreatedOn;
    private String fileUpload;
    private String courseName;
    private String courseFullName;
    private String emailOwner;
    private Boolean hide;
}
