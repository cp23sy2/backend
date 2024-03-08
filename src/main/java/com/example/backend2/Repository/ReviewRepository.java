package com.example.backend2.Repository;

import com.example.backend2.Entity.ReportReview;
import com.example.backend2.Entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query(value = "SELECT r.*, cc.*, c.courseName, c.courseFullName " +
            "FROM review r " +
            "JOIN course c ON r.course_idCourse = c.idCourse " +
            "JOIN category_course cc ON c.category_Course_idCategory_Course = cc.idCategory_Course " +
            "ORDER BY r.reviewCreatedOn DESC", nativeQuery = true)
    List<Review> findAllReviewsWithCategoryName();

//    @Query(value = "select * from review r where r.emailOwner = :emailOwner order by r.reviewCreatedOn desc", nativeQuery = true)
//    List<Review> findAllReviewByEmailOwner(@Param("emailOwner") String emailOwner);

//    @Query("SELECT r FROM Review r " +
//            "JOIN r.courseIdcourse c " +
//            "JOIN c.categoryCourseIdcategoryCourse cc " +
//            "WHERE r.emailOwner = :emailOwner ORDER BY r.reviewCreatedOn DESC")
//    Page<Review> findAllReviewByEmailOwner(@Param("emailOwner") String emailOwner, Pageable pageable);

    @Query(value = "select * from review r " +
            "JOIN course c ON r.course_idCourse = c.idCourse " +
            "JOIN category_course cc ON c.category_Course_idCategory_Course = cc.idCategory_Course " +
            "where r.emailOwner = :emailOwner order by r.reviewCreatedOn desc", nativeQuery = true)
    List<Review> findAllReviewByEmailOwner(@Param("emailOwner") String emailOwner);

    @Query(value = "select DISTINCT r.* from review r " +
            "JOIN report_review rr ON r.idReview = rr.Review_idReview " +
            "order by r.reviewCreatedOn desc ", nativeQuery = true)
    List<Review> findReviewByReviewHaveReport();

    @Query(value = "select * from review r " +
            "JOIN course c ON r.course_idCourse = c.idCourse " +
            "JOIN category_course cc ON c.category_Course_idCategory_Course = cc.idCategory_Course " +
            "where r.hide is true and r.emailOwner = :emailOwner order by r.reviewCreatedOn desc", nativeQuery = true)
    List<Review> findAllReviewByBeHidden(@Param("emailOwner") String emailOwner);
}
