package com.example.backend2.controllers;

import com.example.backend2.Dto.*;
import com.example.backend2.Entity.Review;
import com.example.backend2.Utils.ListMapper;
import com.example.backend2.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasAuthority('st_group')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Review create(@Valid @RequestBody ReviewCreateDto newReview) {
        return reviewService.create(newReview);
    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("")
    public List<ReviewViewAllDto> getReviewAll() {
        return reviewService.getReviewAll();
    }

//    @GetMapping("")
//    public List<ReviewViewAllDto> getAll(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "20") int pageSize) {
//        return reviewService.getReviewAll(page-1, pageSize);
//    }
//
//    @GetMapping("")
//    public Page<ReviewViewAllDto> getAll(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int pageSize) {
//        return reviewService.getReviewAll(page-1, pageSize);
//    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("/{reviewId}")
    public ReviewDetailDto getReviewDetail(@PathVariable Integer reviewId){
        return reviewService.getDetail(reviewId);
    }

    @PreAuthorize("hasAuthority('st_group')")
    @DeleteMapping("/{reviewId}")
    public void delete(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @PreAuthorize("hasAuthority('st_group')")
    @PutMapping("/{reviewId}")
    public Review edit(@Valid @RequestBody ReviewEditDto editReview, @PathVariable Integer reviewId) {
        return reviewService.editReview(editReview,reviewId);
    }

//    @GetMapping("/owner")
//    public Page<ReviewViewAllDto> getReviewAllByEmailOwner(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return reviewService.getReviewAllByEmailOwner(page-1, size);
//    }

    @PreAuthorize("hasAuthority('st_group')")
    @GetMapping("/owner")
    public List<ReviewViewAllDto> getReviewAllByEmailOwner(){
        return reviewService.getReviewAllByEmailOwner();
    }

    @PreAuthorize("hasAuthority('staff_group')")
    @GetMapping("/report")
    public List<ReviewViewAllDto> getReviewHaveReport() {
        return reviewService.getReviewHaveReport();
    }

    @PreAuthorize("hasAuthority('staff_group')")
    @PutMapping("/{reviewId}/hide")
    @ResponseStatus(HttpStatus.OK)
    public void editReviewHideStatus(@PathVariable Integer reviewId) {
        reviewService.editReviewHideStatus(reviewId);
    }

}
