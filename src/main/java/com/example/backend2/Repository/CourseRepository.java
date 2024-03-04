package com.example.backend2.Repository;

import com.example.backend2.Entity.Course;
import com.example.backend2.Entity.CourseFile;
import com.example.backend2.Entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT r FROM Review r WHERE r.courseIdcourse.id = :courseId and r.hide is false ORDER BY r.reviewCreatedOn DESC")
    List<Review> findReviewsByCourseId(Integer courseId);

//    @Query("SELECT r FROM Review r WHERE r.courseIdcourse.id = :courseId ORDER BY r.reviewCreatedOn DESC")
//    Page<Review> findReviewsByCourseId(Integer courseId, Pageable pageable);

    @Query("SELECT cf FROM CourseFile cf WHERE cf.courseIdcourse.id = :courseId and cf.hide is false ORDER BY cf.fileCreatedOn DESC")
    List<CourseFile> findCourseFileByCourseId(Integer courseId);

//    @Query("SELECT cf FROM CourseFile cf WHERE cf.courseIdcourse.id = :courseId ORDER BY cf.fileCreatedOn DESC")
//    Page<CourseFile> findCourseFileByCourseId(Integer courseId, Pageable pageable);
}
