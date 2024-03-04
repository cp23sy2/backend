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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComment", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "emailOwner", nullable = false, length = 100)
    private String emailOwner;

    @Size(max = 1000)
    @NotNull
    @Column(name = "commentDetail", nullable = false, length = 1000)
    private String commentDetail;

    @NotNull
    @Column(name = "commentCreatedOn", nullable = false)
    private Instant commentCreatedOn;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Course_File_idCourse_File", nullable = false)
    private CourseFile courseFileIdcourseFile;

}