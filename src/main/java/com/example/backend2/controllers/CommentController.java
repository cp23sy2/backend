package com.example.backend2.controllers;

import com.example.backend2.Dto.*;
import com.example.backend2.Entity.Comment;
import com.example.backend2.Entity.CourseFile;
import com.example.backend2.Entity.Review;
import com.example.backend2.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

//    V.1 List
//    @GetMapping("")
//    public List<CommentAllDto> getCommentAll() {
//        return commentService.getCommentAll();
//    }

//    V.2 Page
    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("")
    public Page<CommentAllDto> getCommentAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return commentService.getCommentAll(page-1, size);
    }

    @PreAuthorize("hasAuthority('st_group')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@Valid @RequestBody CommentCreateDto newComment) {
        return commentService.create(newComment);
    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("/{commentId}")
    public CommentDetailDto getCommentDetail(@PathVariable Integer commentId){
        return commentService.getDetail(commentId);
    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
    }

    @PreAuthorize("hasAuthority('st_group')")
    @PutMapping("/{commentId}")
    public Comment edit(@Valid @RequestBody CommentEditDto editComment, @PathVariable Integer commentId) {
        return commentService.editComment(editComment,commentId);
    }

    @PreAuthorize("hasAuthority('st_group')")
    @GetMapping("/owner")
    public Page<CommentAllDto> getCommentAllByEmailOwner(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size){
        return commentService.getCommentAllByEmailOwner(page-1, size);
    }

    @PreAuthorize("hasAnyAuthority('staff_group','st_group')")
    @GetMapping("/{courseFileId}/summary")
    public Page<CommentAllDto> getCommentByCourseFileId(
            @PathVariable Integer courseFileId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return commentService.getCommentByCourseFileId(courseFileId, page-1, pageSize);
    }
}
