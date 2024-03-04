package com.example.backend2.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEditDto {
    private Integer id;
    @NotNull
    @NotBlank(message = "Please input comment")
    @Size(max = 1000,message = "Your comment can't over 1000 words")
    private String commentDetail;
}
