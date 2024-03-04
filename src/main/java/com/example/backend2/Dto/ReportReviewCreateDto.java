package com.example.backend2.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportReviewCreateDto {
    @NotNull
    private Boolean inappropriateReview;
    @NotNull
    private Boolean notMatchReview;

    @NotNull
    @Email(message = "Email isn't correct form",regexp = ".+@.+\\.[a-z]+[a-z]+")
    private String emailReportReview;

    @NotNull
    private Integer reviewIdreview;

}
