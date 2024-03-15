package com.example.backend2.Repository;

import com.example.backend2.Entity.CourseFile;
import com.example.backend2.Entity.ReportCoursefile;
import com.example.backend2.Entity.ReportReview;
import com.example.backend2.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportReviewRepository extends JpaRepository<ReportReview, Integer> {

    List<ReportReview> findByReviewIdreview (Review review);
    @Query(value = "SELECT * FROM report_review rr " +
            "WHERE rr.Review_idReview = :reviewId " +
            "ORDER BY rr.reportReviewCreatedOn DESC", nativeQuery = true)
    List<ReportReview> findReportReviewByReviewId(Integer reviewId);


}