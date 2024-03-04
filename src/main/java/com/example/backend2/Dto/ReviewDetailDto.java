package com.example.backend2.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDetailDto {
    private String courseName;
    private String courseFullName;
    private String gradesReceived;
    private Integer section;
    private Integer semester;
    private Integer year;
    private String instructorName;
    private Integer ratingOfInteresting;
    private Integer ratingOfGroupWork;
    private Integer ratingOfGradeCollect;
    private Integer ratingOfEasyExam;
    private Integer ratingOfIndividualWork;
    private String work;
    private String reviewDescription;
    private String emailOwner;
    private Boolean hide;
}
