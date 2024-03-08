package com.example.backend2.services;

import com.example.backend2.Dto.*;
import com.example.backend2.Entity.*;
import com.example.backend2.Repository.CommentRepository;
import com.example.backend2.Repository.CourseFileRepository;
import com.example.backend2.Repository.CourseRepository;
import com.example.backend2.Repository.ReportCoursefileRepository;
import com.example.backend2.Utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseFileService {
    @Autowired
    private CourseFileRepository courseFileRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReportCoursefileRepository reportCoursefileRepository;

    @Autowired
    FilesStorageService storageService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    public CourseFile create(CourseFileCreateDto courseFileNew) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        CourseFile courseFile = modelMapper.map(courseFileNew, CourseFile.class);

        Course course = courseRepository.getById(courseFileNew.getCourse_idCourse());
        courseRepository.findById(courseFileNew.getCourse_idCourse()).orElseThrow(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Check your Course");
        });
        courseFile.setCourseIdcourse(course);
        courseFile.setHide(Boolean.FALSE);

        //file upload and FileCreatedOn
        if(courseFileNew.getFileUpload() != null) {
            if(!courseFileNew.getFileUpload().isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                            .withZone(ZoneId.systemDefault());
                    Instant currentInstant = Instant.now();
//                    String formattedInstant = formatter.format(courseFileNew.getFileCreatedOn());
                    String formattedInstant = formatter.format(currentInstant);

                    String saveNameFile = formattedInstant + courseFileNew.getFileUpload().getOriginalFilename();

                    courseFile.setFileUpload(saveNameFile);
                    courseFile.setFileCreatedOn(currentInstant);

                    storageService.save(courseFileNew.getFileUpload(), formattedInstant);

                } catch (Exception e) {
                    System.out.println(e);
                    String messageFile = "Could not upload the file: " + courseFileNew.getFileUpload().getOriginalFilename() + "!";
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageFile);
                }
            }else{
                courseFile.setFileUpload(null);
            }
        }

        System.out.println(roleMail);
        if(roleMail.getAuthorities().toString().equals("[st_group]")){
            if(!roleMail.getDetails().equals(courseFile.getEmailOwner())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email not same");
            }
        }

        return courseFileRepository.saveAndFlush(courseFile);
    }

    public List<CourseFileAllDto> getCourseFileAll() {
        List<CourseFile> courseFileList = courseFileRepository.findAllCourseFileWithCategoryName();
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

            courseFileAllDtos.add(dto);
        }

        return courseFileAllDtos;
    }

    public CourseFileDetailDto getDetail(Integer id) {
        CourseFile courseFile = courseFileRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does't exist !!"));

        if(courseFile.getFileUpload() != null) {
            storageService.load(courseFile.getFileUpload());
        }

        return modelMapper.map(courseFile, CourseFileDetailDto.class);
    }

    public CourseFile editCourseFile(CourseFileEditDto editCourseFile, Integer courseFileId) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        CourseFile courseFile= courseFileRepository.findById(courseFileId).orElseThrow(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        if(roleMail.getAuthorities().toString().equals("[st_group]")){
            if(!roleMail.getDetails().equals(courseFile.getEmailOwner())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"It's not your post");
            }
        }

        if (courseFile.getHide() == true) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot edit a hidden course file");
        }

        //remove old file and add new file upload
        if(editCourseFile.getFileUpload() != null) {
            if(editCourseFile.getFileUpload().isEmpty() || editCourseFile.getFileUpload() == null) {
                if(courseFile.getFileUpload() != null) { //defend delete null
                    storageService.deleteFile(courseFile.getFileUpload());
                    courseFile.setFileUpload(null);
                }
            }

            else{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                        .withZone(ZoneId.systemDefault());

                Instant currentInstant = Instant.now();
//                    String formattedInstant = formatter.format(courseFileNew.getFileCreatedOn());
                String formattedInstant = formatter.format(currentInstant);

                String saveNameFile = formattedInstant + editCourseFile.getFileUpload().getOriginalFilename();
                if(courseFile.getFileUpload() != null) {
                    storageService.deleteFile(courseFile.getFileUpload());
                }
                storageService.save(editCourseFile.getFileUpload(), formattedInstant);
                courseFile.setFileUpload(saveNameFile);
            }
        }
        courseFile.setTitle(editCourseFile.getTitle());
        courseFile.setFileDescription(editCourseFile.getFileDescription());
        courseFile.setHide(editCourseFile.getHide());

        CourseFile cf = modelMapper.map(courseFile,CourseFile.class);
        return courseFileRepository.saveAndFlush(cf);
    }

    public void deleteCourseFile(@PathVariable Integer id) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        CourseFile courseFile= courseFileRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does't exist !!"));

        if(courseFile.getFileUpload() != null){
            storageService.deleteFile(courseFile.getFileUpload());
        }

        if(roleMail.getAuthorities().toString().equals("[st_group]")){
            if(!roleMail.getDetails().equals(courseFile.getEmailOwner())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"It's not your post");
            }
        }

        List<Comment> comments = commentRepository.findByCourseFileIdcourseFile(courseFile);
        commentRepository.deleteAll(comments);

        List<ReportCoursefile> reportCoursefiles = reportCoursefileRepository.findByCourseFileIdcourseFile(courseFile);
        reportCoursefileRepository.deleteAll(reportCoursefiles);

        courseFileRepository.deleteById(id);
    }

//    public Page<CourseFileAllDto> getCourseFileAllByEmailOwner(int page, int size) {
//        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<CourseFile> courseFilePage = courseFileRepository.findAllCourseFileByEmailOwner(roleMail.getDetails().toString(), pageable);
//
//        return courseFilePage.map(courseFile -> {
//            CourseFileAllDto dto = new CourseFileAllDto();
//            dto.setId(courseFile.getIdCourse_File());
//            dto.setFileDescription(courseFile.getFileDescription());
//            dto.setFileCreatedOn(courseFile.getFileCreatedOn());
//            dto.setFileUpload(courseFile.getFileUpload());
//            dto.setEmailOwner(courseFile.getEmailOwner());
//
//            // Map Course details
//            Course course = courseFile.getCourseIdcourse();
//            if (course != null) {
//                dto.setCourseName(course.getCourseName());
//                dto.setCourseFullName(course.getCourseFullName());
//
//                // Map Category details
//                CategoryCourse categoryCourse = course.getCategoryCourseIdcategoryCourse();
//                if (categoryCourse != null) {
//                    dto.setCategoryName(categoryCourse.getCategoryName());
//                }
//            }
//
//            return dto;
//        });
//    }

    public List<CourseFileAllDto> getCourseFileAllByEmailOwner() {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        List<CourseFile> courseFileList = courseFileRepository.findAllCourseFileByEmailOwner(roleMail.getDetails().toString());
        List<CourseFileAllDto> courseFileAllDtos = new ArrayList<>();

        for (CourseFile courseFile : courseFileList) {
            CourseFileAllDto dto = new CourseFileAllDto();
            dto.setId(courseFile.getIdCourse_File());
            dto.setFileDescription(courseFile.getFileDescription());
            dto.setFileCreatedOn(courseFile.getFileCreatedOn());
            dto.setFileUpload(courseFile.getFileUpload());
            dto.setEmailOwner(courseFile.getEmailOwner());
            dto.setTitle(courseFile.getTitle());
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

            courseFileAllDtos.add(dto);
        }

        return courseFileAllDtos;
    }

    public List<CourseFileAllDto> getCourseFileByCourseFileHaveReport() {
        List<CourseFile> courseFileList = courseFileRepository.findCourseFileByCourseFileHaveReport();
        List<CourseFileAllDto> courseFileAllDtos = new ArrayList<>();

        for (CourseFile courseFile : courseFileList) {
            CourseFileAllDto dto = new CourseFileAllDto();
            dto.setId(courseFile.getIdCourse_File());
            dto.setFileDescription(courseFile.getFileDescription());
            dto.setFileCreatedOn(courseFile.getFileCreatedOn());
            dto.setFileUpload(courseFile.getFileUpload());
            dto.setEmailOwner(courseFile.getEmailOwner());
            dto.setTitle(courseFile.getTitle());
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

            courseFileAllDtos.add(dto);
        }

        return courseFileAllDtos;
    }

    @Transactional
    public void editCourseFileHideStatus(Integer courseFileId) {
        CourseFile courseFile = courseFileRepository.findById(courseFileId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, courseFileId + " does't exist !!"));

        courseFile.setHide(true);
        courseFileRepository.save(courseFile);
    }

    public List<CourseFileAllDto> getCourseFileAllByBeHidden() {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        List<CourseFile> courseFileList = courseFileRepository.findAllCourseFileByBeHidden(roleMail.getDetails().toString());
        List<CourseFileAllDto> courseFileAllDtos = new ArrayList<>();

        for (CourseFile courseFile : courseFileList) {
            CourseFileAllDto dto = new CourseFileAllDto();
            dto.setId(courseFile.getIdCourse_File());
            dto.setFileDescription(courseFile.getFileDescription());
            dto.setFileCreatedOn(courseFile.getFileCreatedOn());
            dto.setFileUpload(courseFile.getFileUpload());
            dto.setEmailOwner(courseFile.getEmailOwner());
            dto.setTitle(courseFile.getTitle());
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

            courseFileAllDtos.add(dto);
        }

        return courseFileAllDtos;
    }

}
