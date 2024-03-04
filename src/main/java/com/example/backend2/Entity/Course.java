package com.example.backend2.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCourse", nullable = false)
    private Integer id;

    @Size(max = 6)
    @NotNull
    @Column(name = "courseName", nullable = false, length = 6)
    private String courseName;

    @Size(max = 100)
    @NotNull
    @Column(name = "courseFullName", nullable = false, length = 100)
    private String courseFullName;

    @Size(max = 500)
    @NotNull
    @Column(name = "courseDescription", nullable = false, length = 500)
    private String courseDescription;

    @NotNull
    @Column(name = "courseCredit", nullable = false)
    private Integer courseCredit;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "semester_idSemester", nullable = false)
    private Semester semesterIdsemester;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_Course_idCategory_Course", nullable = false)
    private CategoryCourse categoryCourseIdcategoryCourse;

}