package com.example.backend2.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEditDto {
    private Integer id;

    @NotNull
    @NotBlank(message = "Please input grade")
    @Size(max = 2,message = "Please enter grade like A, B+, B, C+, C, D+, D")
    @Pattern(regexp = "[A-D]\\+?|F", message = "Invalid rating format")
    private String gradesReceived;

    private Integer section;

    @NotNull
    private Integer semester;

    @NotNull
    @Min(value = 2015, message = "Please input year after 2015")
    private Integer year;

    @NotNull
    @NotBlank(message = "Please input instructor name")
    @Size(max = 100,message = "Instructor name can't over 100 words")
    private String instructorName;

    @NotNull
    @Min(value = 1, message = "Rating Of Interesting must be at least 1")
    @Max(value = 5, message = "Rating Of Interesting must be at most 5")
    private Integer ratingOfInteresting;

    @NotNull
    @Min(value = 1, message = "Rating Of Group Work must be at least 1")
    @Max(value = 5, message = "Rating Of Group Work must be at most 5")
    private Integer ratingOfGroupWork;

    @NotNull
    @Min(value = 1, message = "Rating Of Grade Collection must be at least 1")
    @Max(value = 5, message = "Rating Of Grade Collection must be at most 5")
    private Integer ratingOfGradeCollect;

    @NotNull
    @Min(value = 1, message = "Rating Of Easy to Exam must be at least 1")
    @Max(value = 5, message = "Rating Of Easy to Exam must be at most 5")
    private Integer ratingOfEasyExam;

    @NotNull
    @Min(value = 1, message = "Rating Of Individual Work must be at least 1")
    @Max(value = 5, message = "Rating Of Individual Work must be at most 5")
    private Integer ratingOfIndividualWork;

    @NotNull
    @NotBlank(message = "Please input work")
    @Size(max = 500,message = "Your work can't over 500 words")
    private String work;

    @NotNull
    @NotBlank(message = "Please input your review detail")
    @Size(max = 1000,message = "Your review description can't over 1000 words")
    private String reviewDescription;

    @NotNull
    private Boolean hide;
}
