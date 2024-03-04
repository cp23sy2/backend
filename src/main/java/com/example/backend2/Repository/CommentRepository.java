package com.example.backend2.Repository;

import com.example.backend2.Dto.CommentAllDto;
import com.example.backend2.Entity.Comment;
import com.example.backend2.Entity.CourseFile;
import com.example.backend2.Entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
//    @Query(value = "select * from Comment c " +
//            "JOIN course_File cf ON c.Course_File_idCourse_File = cf.idCourse_File " +
//            "where c.emailOwner = :emailOwner order by c.commentCreatedOn desc", nativeQuery = true)
//    Page<Comment> findAllCommentByEmailOwner(@Param("emailOwner") String emailOwner, Pageable pageable);

    @Query("SELECT c FROM Comment c " +
            "JOIN c.courseFileIdcourseFile cf " +
            "WHERE c.emailOwner = :emailOwner ORDER BY c.commentCreatedOn DESC")
    Page<Comment> findAllCommentByEmailOwner(@Param("emailOwner") String emailOwner, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.courseFileIdcourseFile.idCourse_File = :courseFileId ORDER BY c.commentCreatedOn DESC")
    Page<Comment> findCommentByCourseFileId(Integer courseFileId, Pageable pageable);

    List<Comment> findByCourseFileIdcourseFile(CourseFile courseFile);

}