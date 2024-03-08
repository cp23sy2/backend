package com.example.backend2.services;

import com.example.backend2.Dto.CourseFileAllDto;
import com.example.backend2.Dto.CourseViewDto;
import com.example.backend2.Entity.*;
import com.example.backend2.Repository.CommentRepository;
import com.example.backend2.Repository.CourseFileRepository;
import com.example.backend2.Repository.CourseRepository;
import com.example.backend2.Repository.ReviewRepository;
import com.example.backend2.Utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseFileRepository courseFileRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

//    public List<CourseReviewDto> getCourseAll() {
//        List<Course> courses = courseRepository.findAll();
//        return listMapper.mapList(courses, CourseReviewDto.class, modelMapper);
//    }

    public List<CourseViewDto> getCourseAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseViewDto> courseViewDtos = new ArrayList<>();

        for (Course course : courses) {
            CourseViewDto courseViewDto = modelMapper.map(course, CourseViewDto.class);

            List<Review> reviews = reviewRepository.findAllByCourseIdcourseId(course.getId());
            courseViewDto.setReviewsCount(reviews.size());

            List<CourseFile> courseFiles = courseFileRepository.findAllByCourseIdcourseId(course.getId());
            courseViewDto.setSummariesCount(courseFiles.size());

            courseViewDtos.add(courseViewDto);
        }

        return courseViewDtos;
    }


//    public Page<CourseReviewDto> getCourseAll(Pageable pageable) {
//        List<Course> courses = courseRepository.findAll(pageable).getContent();
//        List<CourseReviewDto> courseReviewDtos = listMapper.mapList(courses, CourseReviewDto.class, modelMapper);
//        return new PageImpl<>(courseReviewDtos, pageable, courseRepository.count());
//    }

//    public Page<CourseReviewDto> getCourseAll(int page, int pageSize) {
//        Pageable pageable = PageRequest.of(page, pageSize);
//        return courseRepository.findCourseAll(pageable);
//    }

    public CourseViewDto getCourseDetail(Integer id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(course, CourseViewDto.class);
    }

    public List<Review> getReviewsByCourseId(Integer courseId) {
        List<Review> reviews = courseRepository.findReviewsByCourseId(courseId);

//        if (reviews.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reviews not found for the specified course");
//        }
        return reviews;
    }

//    public Page<Review> getReviewsByCourseId(Integer courseId, int page, int pageSize) {
//        Pageable pageable = PageRequest.of(page, pageSize);
//        courseRepository.findById(courseId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Don't has this review subject"));
//        return courseRepository.findReviewsByCourseId(courseId, pageable);
//    }

    public List<CourseFileAllDto> getCourseByCourseId(Integer courseId) {
        List<CourseFile> courseFileList = courseRepository.findCourseFileByCourseId(courseId);
        List<CourseFileAllDto> courseFileAllDtos = new ArrayList<>();

        for (CourseFile courseFile : courseFileList) {
            CourseFileAllDto dto = new CourseFileAllDto();
            dto.setTitle(courseFile.getTitle());
            dto.setId(courseFile.getIdCourse_File());
            dto.setFileDescription(courseFile.getFileDescription());
            dto.setFileCreatedOn(courseFile.getFileCreatedOn());
            dto.setFileUpload(courseFile.getFileUpload());
            dto.setEmailOwner(courseFile.getEmailOwner());
            dto.setHide(courseFile.getHide());

            // Map Course details
            Course course = courseFile.getCourseIdcourse();
            if (course != null) {
                dto.setCourseName(course.getCourseName());
                dto.setCourseFullName(course.getCourseFullName());

                // Map Category details
                CategoryCourse categoryCourse = course.getCategoryCourseIdcategoryCourse();
                if (categoryCourse != null) {
                    dto.setCategoryName(categoryCourse.getCategoryName());
                }
            }

            List<Comment> comments = commentRepository.findByCourseFileIdcourseFile(courseFile);
            dto.setCommentCount(comments.size());
            courseFileAllDtos.add(dto);
        }

        return courseFileAllDtos;
    }

//    public Page<CourseFile> getCourseFilesByCourseId(Integer courseId, int page, int pageSize) {
//        Pageable pageable = PageRequest.of(page, pageSize);
//        courseRepository.findById(courseId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Don't has this summary subject"));
//        return courseRepository.findCourseFileByCourseId(courseId, pageable);
//    }

}