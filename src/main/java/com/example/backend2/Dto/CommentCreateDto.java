package com.example.backend2.Dto;

import com.example.backend2.Entity.CourseFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {
    @Email(message = "Email isn't correct form",regexp = ".+@.+\\.[a-z]+[a-z]+")
    @NotNull
    private String emailOwner;

    @NotNull
    @NotBlank(message = "Please input comment")
    @Size(max = 1000,message = "Your comment can't over 1000 words")
    private String commentDetail;

    private Instant commentCreatedOn;

    @NotNull
    private Integer courseFileIdcourseFile;
}
