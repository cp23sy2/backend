package com.example.backend2.Dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseFileEditDto {
    private Integer id;

    @NotNull
    @NotBlank(message = "Please input title of summary")
    @Size(max = 150,message = "Please don't input description of file over 150 words")
    private String title;

    @NotNull
    @NotBlank(message = "Please input description of file")
    @Size(max = 500,message = "Please don't input description of file over 500 words")
    private String fileDescription;

    private MultipartFile fileUpload;

    @NotNull
    private Boolean hide;
}
