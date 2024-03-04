package com.example.backend2.Repository;

import com.example.backend2.Entity.Comment;
import com.example.backend2.Entity.CourseFile;
import com.example.backend2.Entity.ReportCoursefile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportCoursefileRepository extends JpaRepository<ReportCoursefile, Integer> {
    List<ReportCoursefile> findByCourseFileIdcourseFile (CourseFile courseFile);

    @Query(value = "SELECT * FROM report_coursefile rc " +
            "WHERE rc.Course_File_idCourse_File = :courseFileId " +
            "ORDER BY rc.reportCourseFileCreatedOn DESC", nativeQuery = true)
    List<ReportCoursefile> findReportCoursefileByCourseFileId(Integer courseFileId);
}