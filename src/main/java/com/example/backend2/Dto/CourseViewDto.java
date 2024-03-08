package com.example.backend2.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class CourseViewDto {
    private Integer id;
    private String categoryName;
    private String courseName;
    private String courseFullName;
    private Integer courseCredit;
    private Integer reviewsCount;
    private Integer summariesCount;
//    private List<ReviewViewAllDto> reviews;

    // Constructors, getters, setters
}
