package com.example.backend2.controllers;

import com.example.backend2.Dto.CourseFileAllDto;
import com.example.backend2.Dto.CourseViewDto;
import com.example.backend2.Entity.CourseFile;
import com.example.backend2.Entity.Review;
import com.example.backend2.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("")
    public List<CourseViewDto> getCourseAll() {
        return courseService.getCourseAll();
    }

//    @GetMapping("")
//    public Page<CourseReviewDto> getCourseAll(@RequestParam(defaultValue = "1") int page,
//                                              @RequestParam(defaultValue = "9") int pageSize) {
//        Pageable pageable = PageRequest.of(page - 1, pageSize);
//        return courseService.getCourseAll(pageable);
//    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("/{courseId}")
    public CourseViewDto getCourseDetail(@PathVariable Integer courseId){
        return courseService.getCourseDetail(courseId);
    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("/{courseId}/review")
    public List<Review> getReviewsByCourseId(@PathVariable Integer courseId) {
        return courseService.getReviewsByCourseId(courseId);
    }

//    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
//    @GetMapping("/{courseId}/review")
//    public Page<Review> getReviewsByCourseId(
//            @PathVariable Integer courseId,
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int pageSize) {
//        return courseService.getReviewsByCourseId(courseId, page-1, pageSize);
//    }

    @GetMapping("/{courseId}/summary")
    public List<CourseFileAllDto> getCourseByCourseId(@PathVariable Integer courseId) {
        return courseService.getCourseByCourseId(courseId);
    }

//    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
//    @GetMapping("/{courseId}/summary")
//    public Page<CourseFile> getCourseFileByCourseId(
//            @PathVariable Integer courseId,
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int pageSize) {
//        return courseService.getCourseFilesByCourseId(courseId, page-1, pageSize);
//    }
}