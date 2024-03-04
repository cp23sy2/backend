package com.example.backend2.Dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.Instant;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseFileCreateDto {
    @NotNull
    @NotBlank(message = "Please input title of summary")
    @Size(max = 150,message = "Please don't input description of file over 150 words")
    private String title;

    @NotNull
    @NotBlank(message = "Please input description of file")
    @Size(max = 500,message = "Please don't input description of file over 500 words")
    private String fileDescription;

    @Email(message = "Email isn't correct form",regexp = ".+@.+\\.[a-z]+[a-z]+")
    @NotNull
    private String emailOwner;

    private Instant fileCreatedOn;

    @NotNull
    private Integer course_idCourse;

    @NotNull
    private MultipartFile fileUpload;

    private Boolean hide;
}
