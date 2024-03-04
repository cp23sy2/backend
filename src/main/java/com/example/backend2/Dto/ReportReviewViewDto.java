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
public class ReportReviewViewDto {
    private Integer id;
    private Boolean inappropriateReview;
    private Boolean notMatchReview;
    private Instant reportReviewCreatedOn;
    private String emailReportReview;
    private Integer idReview;

}
