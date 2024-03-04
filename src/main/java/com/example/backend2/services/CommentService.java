package com.example.backend2.services;

import com.example.backend2.Dto.*;
import com.example.backend2.Entity.Comment;
import com.example.backend2.Entity.Course;
import com.example.backend2.Entity.CourseFile;
import com.example.backend2.Entity.Review;
import com.example.backend2.Repository.CommentRepository;
import com.example.backend2.Repository.CourseFileRepository;
import com.example.backend2.Repository.CourseRepository;
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
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CourseFileRepository courseFileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

//    V.1 List
//    public List<CommentAllDto> getCommentAll() {
//        List<Comment> CommentAllList = commentRepository.findAll(Sort.by("commentCreatedOn").descending());
//        return listMapper.mapList(CommentAllList, CommentAllDto.class, modelMapper);
//    }

//    V.2 Page
    public Page<CommentAllDto> getCommentAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("commentCreatedOn").descending());
        Page<Comment> commentPage = commentRepository.findAll(pageable);
        return commentPage.map(comment -> modelMapper.map(comment, CommentAllDto.class));
    }

    public Comment create(CommentCreateDto commentNew){
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        Comment comment = modelMapper.map(commentNew, Comment.class);
        CourseFile courseFile = courseFileRepository.getById(commentNew.getCourseFileIdcourseFile());
        courseFileRepository.findById(commentNew.getCourseFileIdcourseFile()).orElseThrow(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Check your Summary");
        });
        comment.setCourseFileIdcourseFile(courseFile);

        Instant currentInstant = Instant.now();
        comment.setCommentCreatedOn(currentInstant);

        System.out.println(roleMail);
        if(roleMail.getAuthorities().toString().equals("[st_group]")){
            if(!roleMail.getDetails().equals(comment.getEmailOwner())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email not same");
            }
        }

        return commentRepository.saveAndFlush(comment);
    }

    public CommentDetailDto getDetail(Integer id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does't exist !!"));
        return modelMapper.map(comment, CommentDetailDto.class);
    }

    public void deleteComment(@PathVariable Integer commentId) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        commentId + " does't exist !!"));

        System.out.println(roleMail);
        if(roleMail.getAuthorities().toString().equals("[st_group]")){
            if(!roleMail.getDetails().equals(comment.getEmailOwner())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"It's not your post");
            }
        }

        commentRepository.deleteById(commentId);
    }

    public Comment editComment(CommentEditDto editComment, Integer id) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does't exist !!"));

        comment.setCommentDetail(editComment.getCommentDetail());

        System.out.println(roleMail);
        if(roleMail.getAuthorities().toString().equals("[st_group]")){
            if(!roleMail.getDetails().equals(comment.getEmailOwner())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"It's not your post");
            }
        }

        return commentRepository.saveAndFlush(comment);
    }

    public Page<CommentAllDto> getCommentAllByEmailOwner(int page, int size) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        Pageable pageable = PageRequest.of(page, size, Sort.by("commentCreatedOn").descending());
        Page<Comment> commentPage = commentRepository.findAllCommentByEmailOwner(roleMail.getDetails().toString(),pageable);
        return commentPage.map(comment -> modelMapper.map(comment, CommentAllDto.class));
    }

    public Page<CommentAllDto> getCommentByCourseFileId(Integer courseFileId, int page, int pageSize) {
        courseFileRepository.findById(courseFileId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        courseFileId + " does't exist !!"));

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Comment> commentPage = commentRepository.findCommentByCourseFileId(courseFileId, pageable);
        return commentPage.map(comment -> modelMapper.map(comment, CommentAllDto.class));
    }
}