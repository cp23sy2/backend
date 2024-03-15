package com.example.backend2.controllers;

import com.example.backend2.Dto.ReportCourseFileCreateDto;
import com.example.backend2.Dto.ReportCourseFileViewDto;
import com.example.backend2.Dto.ReportReviewCreateDto;
import com.example.backend2.Dto.ReportReviewViewDto;
import com.example.backend2.Entity.ReportCoursefile;
import com.example.backend2.services.ReportCourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ReportCourseFile")
public class ReportCourseFileController {
    @Autowired
    private ReportCourseFileService reportCourseFileService;

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("")
    public List<ReportCourseFileViewDto> getReportCourseFileAll() {
        return reportCourseFileService.getReportCourseFileAll();
    }

//    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
//    @GetMapping("")
//    public Page<ReportCourseFileViewDto> getReportReviewAll(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return reportCourseFileService.getReportCourseFileAll(page-1, size);
//    }

    @PreAuthorize("hasAuthority('st_group')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ReportCoursefile create(@Valid @RequestBody ReportCourseFileCreateDto newReportCourseFile) {
        return reportCourseFileService.create(newReportCourseFile);
    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("/{reportCourseFileId}")
    public ReportCourseFileViewDto getReportCourseFileDetail(@PathVariable Integer reportCourseFileId) {
        return reportCourseFileService.getDetail(reportCourseFileId);
    }

    @PreAuthorize("hasAuthority('staff_group')")
    @DeleteMapping("/{reportCourseFileId}")
    public void delete(@PathVariable Integer reportCourseFileId) {
        reportCourseFileService.deleteReportCourseFile(reportCourseFileId);
    }

//    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
//    @GetMapping("/{courseFileId}/summary")
//    public List<ReportCourseFileViewDto> getReportCourseFilebyCourseFileId(Integer courseFileId) {
//        return reportCourseFileService.getReportCourseFilebyCourseFileId(courseFileId);
//    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("/{courseFileId}/summary")
    public List<ReportCourseFileViewDto> getReportCourseFileByCourseFileId(@PathVariable Integer courseFileId) {
        return reportCourseFileService.getReportCourseFileByCourseFileId(courseFileId);
    }

    @PreAuthorize("hasAuthority('staff_group')")
    @DeleteMapping("/{courseFileId}/summary")
    public void deleteReportCourseFileByCourseFileId(@PathVariable Integer courseFileId) {
        reportCourseFileService.deleteReportCourseFileByCourseFileId(courseFileId);
    }
}
