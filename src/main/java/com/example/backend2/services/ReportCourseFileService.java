package com.example.backend2.services;

import com.example.backend2.Dto.ReportCourseFileCreateDto;
import com.example.backend2.Dto.ReportCourseFileViewDto;
import com.example.backend2.Dto.ReportReviewCreateDto;
import com.example.backend2.Dto.ReportReviewViewDto;
import com.example.backend2.Entity.CourseFile;
import com.example.backend2.Entity.ReportCoursefile;
import com.example.backend2.Entity.ReportReview;
import com.example.backend2.Repository.CourseFileRepository;
import com.example.backend2.Repository.ReportCoursefileRepository;
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
public class ReportCourseFileService {
    @Autowired
    private ReportCoursefileRepository reportCoursefileRepository;

    @Autowired
    private CourseFileRepository courseFileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    public ReportCoursefile create(ReportCourseFileCreateDto reportCourseFileNew){
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        ReportCoursefile reportCourseFile = modelMapper.map(reportCourseFileNew, ReportCoursefile.class);
        CourseFile courseFile = courseFileRepository.getById(reportCourseFileNew.getCourseFileIdcourseFile());
        courseFileRepository.findById(reportCourseFileNew.getCourseFileIdcourseFile()).orElseThrow(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Check your Summary");
        });

        Instant currentInstant = Instant.now();
        reportCourseFile.setReportCourseFileCreatedOn(currentInstant);
        reportCourseFile.setCourseFileIdcourseFile(courseFile);

        if(reportCourseFile.getInappropriateCourseFile() == false && reportCourseFile.getNotMatchCourseFile() == false ||
                reportCourseFile.getInappropriateCourseFile() == true && reportCourseFile.getNotMatchCourseFile() == true) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please send 1 detail to report");
        }

        if(roleMail.getAuthorities().toString().equals("[st_group]")){
            if(!roleMail.getDetails().equals(reportCourseFile.getEmailReportCourseFile())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"It's not your report");
            }
        }

        return reportCoursefileRepository.saveAndFlush(reportCourseFile);
    }

    public List<ReportCourseFileViewDto> getReportCourseFileAll() {
        List<ReportCoursefile> reportCoursefileList = reportCoursefileRepository.findAll(Sort.by("reportCourseFileCreatedOn").descending());
        List<ReportCourseFileViewDto> viewDtoList = new ArrayList<>();

        for (ReportCoursefile reportCoursefile : reportCoursefileList) {
            ReportCourseFileViewDto viewDto = modelMapper.map(reportCoursefile, ReportCourseFileViewDto.class);
            viewDto.setIdCourse_File(reportCoursefile.getCourseFileIdcourseFile().getIdCourse_File());
            viewDtoList.add(viewDto);
        }

        return viewDtoList;

    }

//    public Page<ReportCourseFileViewDto> getReportCourseFileAll(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("reportCourseFileCreatedOn").descending());
//        Page<ReportCoursefile> reportCoursefilePage = reportCoursefileRepository.findAll(pageable);
//        return reportCoursefilePage.map(reportCoursefile -> {
//            ReportCourseFileViewDto viewDto = modelMapper.map(reportCoursefile, ReportCourseFileViewDto.class);
//            viewDto.setIdCourse_File(reportCoursefile.getCourseFileIdcourseFile().getIdCourse_File());
//            return viewDto;
//        });
//    }

    public ReportCourseFileViewDto getDetail(Integer id) {
        ReportCoursefile reportCoursefile = reportCoursefileRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does't exist !!"));
        ReportCourseFileViewDto viewDto = modelMapper.map(reportCoursefile, ReportCourseFileViewDto.class);
        viewDto.setIdCourse_File(reportCoursefile.getCourseFileIdcourseFile().getIdCourse_File());
        return viewDto;
    }

    public void deleteReportCourseFile(@PathVariable Integer reportCourseFileId) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        ReportCoursefile reportCoursefile = reportCoursefileRepository.findById(reportCourseFileId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        reportCourseFileId + " does't exist !!"));

        if(roleMail.getAuthorities().toString().equals("[st_group]")){
            if(!roleMail.getDetails().equals(reportCoursefile.getEmailReportCourseFile())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"It's not your report");
            }
        }

        reportCoursefileRepository.deleteById(reportCourseFileId);
    }

    public void deleteReportCourseFileByCourseFileId(Integer courseFileId) {
        List<ReportCoursefile> reportCoursefilesToDelete = reportCoursefileRepository.findReportCoursefileByCourseFileId(courseFileId);
        reportCoursefileRepository.deleteAll(reportCoursefilesToDelete);
    }

//    public List<ReportCourseFileViewDto> getReportCourseFilebyCourseFileId(Integer courseFileId) {
//        courseFileRepository.findById(courseFileId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//                        courseFileId + " does't exist !!"));
//        List<ReportCoursefile> reportCoursefileList = reportCoursefileRepository.findReportCoursefileByCourseFileId(courseFileId);
//        List<ReportCourseFileViewDto> viewDtoList = new ArrayList<>();
//        for (ReportCoursefile reportCoursefile : reportCoursefileList) {
//            ReportCourseFileViewDto viewDto = modelMapper.map(reportCoursefile, ReportCourseFileViewDto.class);
//            viewDto.setIdCourse_File(reportCoursefile.getCourseFileIdcourseFile().getIdCourse_File());
//            viewDtoList.add(viewDto);
//        }
//
//        return viewDtoList;
//    }

    public List<ReportCourseFileViewDto> getReportCourseFileByCourseFileId(Integer courseFileId) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        List<ReportCoursefile> reportCoursefileList = reportCoursefileRepository.findReportCoursefileByCourseFileId(courseFileId);
        List<ReportCourseFileViewDto> viewDtoList = new ArrayList<>();

        for (ReportCoursefile reportCoursefile : reportCoursefileList) {
            ReportCourseFileViewDto viewDto = modelMapper.map(reportCoursefile, ReportCourseFileViewDto.class);
            viewDto.setIdCourse_File(reportCoursefile.getCourseFileIdcourseFile().getIdCourse_File());
            if (roleMail.getAuthorities().toString().equals("[staff_group]")) {
                viewDto.setEmailReportCourseFile(reportCoursefile.getEmailReportCourseFile());
            } else {
                viewDto.setEmailReportCourseFile("Anonymous");
            }
            viewDtoList.add(viewDto);
        }

        return viewDtoList;
    }
}
