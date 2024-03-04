package com.example.backend2.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "report_coursefile")
public class ReportCoursefile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReport_CourseFile", nullable = false)
    private Integer idReport_CourseFile;

    @NotNull
    @Column(name = "inappropriateCourseFile", nullable = false)
    private Boolean inappropriateCourseFile;

    @NotNull
    @Column(name = "notMatchCourseFile", nullable = false)
    private Boolean notMatchCourseFile;

    @NotNull
    @Column(name = "reportCourseFileCreatedOn", nullable = false)
    private Instant reportCourseFileCreatedOn;

    @Size(max = 100)
    @NotNull
    @Column(name = "emailReportCourseFile", nullable = false, length = 100)
    private String emailReportCourseFile;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Course_File_idCourse_File", nullable = false)
    private CourseFile courseFileIdcourseFile;

}