package com.example.backend2.services;

import com.example.backend2.Dto.ReportReviewCreateDto;
import com.example.backend2.Dto.ReportReviewViewDto;
import com.example.backend2.Entity.ReportCoursefile;
import com.example.backend2.Entity.ReportReview;
import com.example.backend2.Entity.Review;
import com.example.backend2.Repository.ReportReviewRepository;
import com.example.backend2.Repository.ReviewRepository;
import com.example.backend2.Utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportReviewService {

    @Autowired
    private ReportReviewRepository reportReviewRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    public ReportReview create(ReportReviewCreateDto reportReviewNew){
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        ReportReview reportReview = modelMapper.map(reportReviewNew, ReportReview.class);
        Review review = reviewRepository.getById(reportReviewNew.getReviewIdreview());
        reviewRepository.findById(reportReviewNew.getReviewIdreview()).orElseThrow(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Check your Review");
        });

        Instant currentInstant = Instant.now();
        reportReview.setReportReviewCreatedOn(currentInstant);
        reportReview.setReviewIdreview(review);

        if(reportReviewNew.getInappropriateReview() == false && reportReviewNew.getNotMatchReview() == false ||
                reportReviewNew.getInappropriateReview() == true && reportReviewNew.getNotMatchReview() == true) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please send 1 detail to report");
        }

        if(roleMail.getAuthorities().toString().equals("[st_group]")){
            if(!roleMail.getDetails().equals(reportReview.getEmailReportReview())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"It's not your report");
            }
        }

        return reportReviewRepository.saveAndFlush(reportReview);
    }

//    V.1 List
    public List<ReportReviewViewDto> getReportReviewAll() {
        List<ReportReview> reportReviewList = reportReviewRepository.findAll(Sort.by("reportReviewCreatedOn").descending());
        return listMapper.mapList(reportReviewList, ReportReviewViewDto.class, modelMapper);
    }

//    V.2 Page
//    public Page<ReportReviewViewDto> getReportReviewAll(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("reportReviewCreatedOn").descending());
//        Page<ReportReview> reportReviewPage = reportReviewRepository.findAll(pageable);
//        return reportReviewPage.map(reportReview -> modelMapper.map(reportReview, ReportReviewViewDto.class));
//    }

    public ReportReviewViewDto getDetail(Integer id) {
        ReportReview reportReview = reportReviewRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does't exist !!"));
        return modelMapper.map(reportReview, ReportReviewViewDto.class);
    }

    public void deleteReportReview(@PathVariable Integer reportReviewId) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        ReportReview reportReview = reportReviewRepository.findById(reportReviewId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        reportReviewId + " does't exist !!"));

        if(roleMail.getAuthorities().toString().equals("[st_group]")){
            if(!roleMail.getDetails().equals(reportReview.getEmailReportReview())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"It's not your report");
            }
        }

        reportReviewRepository.deleteById(reportReviewId);
    }

    public void deleteReportReviewByReviewId(Integer reviewId) {
        List<ReportReview> reportReportToDelete = reportReviewRepository.findReportReviewByReviewId(reviewId);
        reportReviewRepository.deleteAll(reportReportToDelete);
    }

    public List<ReportReviewViewDto> getReportReviewByReviewId(Integer reviewId) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        List<ReportReview> reportReviewList = reportReviewRepository.findReportReviewByReviewId(reviewId);
        List<ReportReviewViewDto> viewDtoList = new ArrayList<>();

        for (ReportReview reportReview : reportReviewList) {
            ReportReviewViewDto viewDto = modelMapper.map(reportReview, ReportReviewViewDto.class);
            viewDto.setIdReview(reportReview.getReviewIdreview().getId());
            if (roleMail.getAuthorities().toString().equals("[staff_group]")) {
                viewDto.setEmailReportReview(reportReview.getEmailReportReview());
            } else {
                viewDto.setEmailReportReview("Anonymous");
            }
            viewDtoList.add(viewDto);
        }

        return viewDtoList;
    }


}
