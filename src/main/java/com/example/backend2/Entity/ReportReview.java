package com.example.backend2.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "report_review")
public class ReportReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReport_Review", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "inappropriateReview", nullable = false)
    private Boolean inappropriateReview;

    @NotNull
    @Column(name = "notMatchReview", nullable = false)
    private Boolean notMatchReview;

    @NotNull
    @Column(name = "reportReviewCreatedOn", nullable = false)
    private Instant reportReviewCreatedOn;

    @Size(max = 100)
    @NotNull
    @Column(name = "emailReportReview", nullable = false, length = 100)
    private String emailReportReview;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Review_idReview", nullable = false)
    private Review reviewIdreview;

}