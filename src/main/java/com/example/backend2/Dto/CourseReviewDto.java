package com.example.backend2.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Getter
@Setter
public class CourseReviewDto {
    private Integer id;
    private String categoryName;
    private String courseName;
    private String courseFullName;
    private Integer courseCredit;
    private String emailOwner;
//    private List<ReviewViewAllDto> reviews;

    // Constructors, getters, setters
}
