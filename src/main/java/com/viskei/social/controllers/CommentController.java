package com.viskei.social.controllers;

import com.viskei.social.exception.CommentNotFoundException;
import com.viskei.social.payload.request.CommentRequest;
import com.viskei.social.payload.response.CommentResponse;
import com.viskei.social.payload.response.MessageResponse;
import com.viskei.social.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getComment(@Valid @RequestBody CommentRequest commentRequest) {
        CommentResponse comment;

        try {
            comment = commentService.getComment(commentRequest);
        } catch (CommentNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("ERROR: Comment is not found!"));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("ERROR: The given id must not be null!"));
        }

        return ResponseEntity.ok().body(comment);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllCommentByUsername(@RequestBody CommentRequest commentRequest) {
        List<CommentResponse> comments = commentService.getAllCommentByUsername(commentRequest);
        return ResponseEntity.ok().body(comments);
    }

    @PatchMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateComment(@Valid @RequestBody CommentRequest commentRequest) {
        CommentResponse comment;

        try {
            comment = commentService.updateComment(commentRequest);
        } catch (CommentNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("ERROR: Comment is not found!"));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("ERROR: The given id must not be null!"));
        }

        return ResponseEntity.ok().body(comment);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteComment(@Valid @RequestBody CommentRequest commentRequest) {
        CommentResponse comment;

        try {
            comment = commentService.deleteComment(commentRequest);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("ERROR: The given id must not be null!"));
        } catch (CommentNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("ERROR: Comment is not found!"));
        }

        return ResponseEntity.ok().body(comment);
    }
}
