package com.example.backend2.controllers;

import com.example.backend2.Dto.ReportReviewCreateDto;
import com.example.backend2.Dto.ReportReviewViewDto;
import com.example.backend2.Entity.ReportReview;
import com.example.backend2.services.ReportReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ReportReview")
public class ReportReviewController {
    @Autowired
    private ReportReviewService reportReviewService;

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("")
    public List<ReportReviewViewDto> getReportReviewAll() {
        return reportReviewService.getReportReviewAll();
    }

//    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
//    @GetMapping("")
//    public Page<ReportReviewViewDto> getReportReviewAll(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return reportReviewService.getReportReviewAll(page-1, size);
//    }

    @PreAuthorize("hasAuthority('st_group')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ReportReview create(@Valid @RequestBody ReportReviewCreateDto newReportReview) {
        return reportReviewService.create(newReportReview);
    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("/{reportReviewId}")
    public ReportReviewViewDto getReportReviewDetail(@PathVariable Integer reportReviewId) {
        return reportReviewService.getDetail(reportReviewId);
    }

    @PreAuthorize("hasAuthority('staff_group')")
    @DeleteMapping("/{reportReviewId}")
    public void delete(@PathVariable Integer reportReviewId) {
        reportReviewService.deleteReportReview(reportReviewId);
    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("/{reviewId}/review")
    public List<ReportReviewViewDto> getReportReviewByReviewId(@PathVariable Integer reviewId) {
        return reportReviewService.getReportReviewByReviewId(reviewId);
    }

}
