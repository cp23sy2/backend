package com.example.backend2.Entity;

import com.example.backend2.Dto.ReviewCreateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReview", nullable = false)
    private Integer id;

    @Size(max = 2)
    @NotNull
    @Column(name = "gradesReceived", nullable = false, length = 2)
    private String gradesReceived;

//    @NotNull
    @Column(name = "section", nullable = true)
    private Integer section;

    @NotNull
    @Column(name = "semester", nullable = false)
    private Integer semester;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @Size(max = 100)
    @NotNull
    @Column(name = "instructorName", nullable = false, length = 100)
    private String instructorName;

    @NotNull
    @Column(name = "ratingOfInteresting", nullable = false)
    private Integer ratingOfInteresting;

    @NotNull
    @Column(name = "ratingOfGroupWork", nullable = false)
    private Integer ratingOfGroupWork;

    @NotNull
    @Column(name = "ratingOfGradeCollect", nullable = false)
    private Integer ratingOfGradeCollect;

    @NotNull
    @Column(name = "ratingOfEasyExam", nullable = false)
    private Integer ratingOfEasyExam;

    @NotNull
    @Column(name = "ratingOfIndividualWork", nullable = false)
    private Integer ratingOfIndividualWork;

    @Size(max = 500)
    @NotNull
    @Column(name = "work", nullable = false, length = 500)
    private String work;

    @Size(max = 100)
    @NotNull
    @Column(name = "emailOwner", nullable = false, length = 100)
    private String emailOwner;

    @Size(max = 1000)
    @NotNull
    @Column(name = "reviewDescription", nullable = false, length = 1000)
    private String reviewDescription;

    @NotNull
    @CreationTimestamp
    @Column(name = "reviewCreatedOn", nullable = false)
    private Instant reviewCreatedOn;

    @NotNull
    @Column(name = "hide", nullable = false)
    private Boolean hide;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "course_idCourse", nullable = false)
    private Course courseIdcourse;

}