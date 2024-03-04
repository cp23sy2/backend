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
@Table(name = "course_file")
public class CourseFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCourse_File", nullable = false)
    private Integer idCourse_File;

    @Size(max = 150)
    @NotNull
    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Size(max = 500)
    @NotNull
    @Column(name = "fileDescription", nullable = false, length = 500)
    private String fileDescription;

    @NotNull
    @Column(name = "fileCreatedOn", nullable = false)
    private Instant fileCreatedOn;

    @Size(max = 100)
    @NotNull
    @Column(name = "emailOwner", nullable = false, length = 100)
    private String emailOwner;

    @Size(max = 100)
    @NotNull
    @Column(name = "fileUpload", nullable = false, length = 100)
    private String fileUpload;

    @NotNull
    @Column(name = "hide", nullable = false)
    private Boolean hide;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_idCourse", nullable = false)
    private Course courseIdcourse;

}