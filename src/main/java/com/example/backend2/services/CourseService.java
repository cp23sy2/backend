package com.example.backend2.services;

import com.example.backend2.Dto.CourseReviewDto;
import com.example.backend2.Entity.Course;
import com.example.backend2.Entity.CourseFile;
import com.example.backend2.Entity.Review;
import com.example.backend2.Repository.CourseRepository;
import com.example.backend2.Utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    public List<CourseReviewDto> getCourseAll() {
        List<Course> courses = courseRepository.findAll();
        return listMapper.mapList(courses, CourseReviewDto.class, modelMapper);
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

    public CourseReviewDto getCourseDetail(Integer id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(course, CourseReviewDto.class);
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

    public List<CourseFile> getCourseByCourseId(Integer courseId) {
        List<CourseFile> courseFile = courseRepository.findCourseFileByCourseId(courseId);

//        if (courseFile.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reviews not found for the specified course");
//        }
        return courseFile;
    }

//    public Page<CourseFile> getCourseFilesByCourseId(Integer courseId, int page, int pageSize) {
//        Pageable pageable = PageRequest.of(page, pageSize);
//        courseRepository.findById(courseId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Don't has this summary subject"));
//        return courseRepository.findCourseFileByCourseId(courseId, pageable);
//    }

}