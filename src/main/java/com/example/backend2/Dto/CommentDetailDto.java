package com.example.backend2.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetailDto {
    private Integer id;
    private String emailOwner;
    private String commentDetail;
    private Instant commentCreatedOn;
    private Integer idCourse_File;
}
