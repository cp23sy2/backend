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
public class ReportCourseFileViewDto {
    private Integer id;
    private Boolean inappropriateCourseFile;
    private Boolean notMatchCourseFile;
    private Instant reportCourseFileCreatedOn;
    private String emailReportCourseFile;
    private Integer idCourse_File;
}
