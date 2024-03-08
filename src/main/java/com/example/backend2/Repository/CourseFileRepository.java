package com.example.backend2.Repository;

import com.example.backend2.Entity.CourseFile;
import com.example.backend2.Entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseFileRepository extends JpaRepository<CourseFile, Integer> {
    @Query(value = "SELECT cf.*, cc.*, c.courseName, c.courseFullName " +
            "FROM course_file cf " +
            "JOIN course c ON cf.course_idCourse = c.idCourse " +
            "JOIN category_course cc ON c.category_Course_idCategory_Course = cc.idCategory_Course " +
            "ORDER BY cf.fileCreatedOn DESC", nativeQuery = true)
    List<CourseFile> findAllCourseFileWithCategoryName();

    @Query(value = "SELECT cf.*, cc.*, c.courseName, c.courseFullName " +
            "FROM course_file cf " +
            "JOIN course c ON cf.course_idCourse = c.idCourse " +
            "JOIN category_course cc ON c.category_Course_idCategory_Course = cc.idCategory_Course " +
            "ORDER BY cf.fileCreatedOn DESC", nativeQuery = true)
    Page<CourseFile> findAllCourseFileWithCategoryNamePage(Pageable pageable);

//    @Query("SELECT cf FROM CourseFile cf " +
//            "JOIN cf.courseIdcourse c " +
//            "JOIN c.categoryCourseIdcategoryCourse cc " +
//            "WHERE cf.emailOwner = :emailOwner ORDER BY cf.fileCreatedOn DESC")
//    Page<CourseFile> findAllCourseFileByEmailOwner(@Param("emailOwner") String emailOwner, Pageable pageable);

    @Query(value = "select * from course_file cf " +
            "JOIN course c ON cf.course_idCourse = c.idCourse " +
            "JOIN category_course cc ON c.category_Course_idCategory_Course = cc.idCategory_Course " +
            "where cf.emailOwner = :emailOwner order by cf.fileCreatedOn desc", nativeQuery = true)
    List<CourseFile> findAllCourseFileByEmailOwner(@Param("emailOwner") String emailOwner);

    @Query(value = "select DISTINCT cf.* from course_file cf " +
            "JOIN report_coursefile rc ON cf.idCourse_File = rc.Course_File_idCourse_File " +
            "order by cf.fileCreatedOn desc ", nativeQuery = true)
    List<CourseFile> findCourseFileByCourseFileHaveReport();

    @Query(value = "select * from course_file cf " +
            "JOIN course c ON cf.course_idCourse = c.idCourse " +
            "JOIN category_course cc ON c.category_Course_idCategory_Course = cc.idCategory_Course " +
            "where cf.hide is true and cf.emailOwner = :emailOwner order by cf.fileCreatedOn desc", nativeQuery = true)
    List<CourseFile> findAllCourseFileByBeHidden(@Param("emailOwner") String emailOwner);


}
