package com.example.backend2.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCourseFileCreateDto {
    @NotNull
    private Boolean inappropriateCourseFile;

    @NotNull
    private Boolean notMatchCourseFile;

    @NotNull
    @Email(message = "Email isn't correct form",regexp = ".+@.+\\.[a-z]+[a-z]+")
    private String emailReportCourseFile;

    @NotNull
    private Integer courseFileIdcourseFile;
}
