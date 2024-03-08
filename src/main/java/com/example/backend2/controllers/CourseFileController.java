package com.example.backend2.controllers;

import com.example.backend2.Dto.*;
import com.example.backend2.Entity.CourseFile;
import com.example.backend2.Repository.CourseRepository;
import com.example.backend2.services.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/summary")
public class CourseFileController {
    @Autowired
    private CourseFileService courseFileService;

    @PreAuthorize("hasAuthority('st_group')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseFile create(@Valid @ModelAttribute CourseFileCreateDto newCourseFile) {
        return courseFileService.create(newCourseFile);
    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("")
    public List<CourseFileAllDto> getCourseFileAll() {
        return courseFileService.getCourseFileAll();
    }

//    @GetMapping("")
//    public Page<CourseFileAllDto> getAll(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int pageSize) {
//        return courseFileService.getCourseFileAll(page-1, pageSize);
//    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("/{courseFileId}")
    public CourseFileDetailDto getCourseFileDetail(@PathVariable Integer courseFileId){
        return courseFileService.getDetail(courseFileId);
    }

    @PreAuthorize("hasAuthority('st_group')")
    @PutMapping("/{courseFileId}")
    public CourseFile edit(@Valid @ModelAttribute CourseFileEditDto editCourseFile, @PathVariable Integer courseFileId) {
        return courseFileService.editCourseFile(editCourseFile,courseFileId);
    }

    @PreAuthorize("hasAuthority('st_group')")
    @DeleteMapping("/{courseFileId}")
    public void delete(@PathVariable Integer courseFileId) {
        courseFileService.deleteCourseFile(courseFileId);
    }

//    @PreAuthorize("hasAuthority('st_group')")
//    @GetMapping("/owner")
//    public Page<CourseFileAllDto> getCourseFileAllByEmailOwner(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size
//    ){
//        return courseFileService.getCourseFileAllByEmailOwner(page-1, size);
//    }

    @PreAuthorize("hasAuthority('st_group')")
    @GetMapping("/owner")
    public List<CourseFileAllDto> getCourseFileAllByEmailOwner(){
        return courseFileService.getCourseFileAllByEmailOwner();
    }

    @PreAuthorize("hasAuthority('staff_group')")
    @GetMapping("/report")
    public List<CourseFileAllDto> getCourseFileByCourseFileHaveReport(){
        return courseFileService.getCourseFileByCourseFileHaveReport();
    }

    @PreAuthorize("hasAuthority('staff_group')")
    @PutMapping("/{courseFileId}/hide")
    @ResponseStatus(HttpStatus.OK)
    public void hideCourseFile(@PathVariable Integer courseFileId) {
        courseFileService.editCourseFileHideStatus(courseFileId);
    }

    @PreAuthorize("hasAuthority('st_group')")
    @GetMapping("/hidden")
    public List<CourseFileAllDto> getCourseFileAllByBeHidden(){
        return courseFileService.getCourseFileAllByBeHidden();
    }
}
