package com.viskei.social.controllers;

import com.viskei.social.exception.PostNotFoundException;
import com.viskei.social.payload.request.PostRequest;
import com.viskei.social.payload.response.MessageResponse;
import com.viskei.social.payload.response.PostResponse;
import com.viskei.social.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        PostResponse post;

        try {
            post = postService.createPost(postRequest);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/comment")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createComment(@RequestBody PostRequest postRequest) {
        PostResponse post;

        try {
            post = postService.createComment(postRequest);
        } catch (IllegalArgumentException | PostNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.ok().body(post);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getPost(@RequestBody PostRequest postRequest) {
        PostResponse post;

        try {
            post = postService.getPost(postRequest);
        } catch (IllegalArgumentException | PostNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllPostsByTags(@RequestBody(required = false) PostRequest postRequest) {
        List<PostResponse> post;

        try {
            post = postService.getAllPostsByTags(postRequest);
        } catch (IllegalArgumentException | PostNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/comment")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllCommentsByPost(@RequestBody PostRequest postRequest) {
        PostResponse post;

        try {
            post = postService.getAllCommentsByPost(postRequest);
        } catch (IllegalArgumentException | PostNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.ok().body(post);
    }

    @PatchMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updatePost(@RequestBody PostRequest postRequest) {
        PostResponse post;

        try {
            post = postService.updatePost(postRequest);
        } catch (IllegalArgumentException | PostNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.ok().body(post);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deletePost(@RequestBody PostRequest postRequest) {
        PostResponse post;

        try {
            post = postService.deletePost(postRequest);
        } catch (IllegalArgumentException | PostNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.ok().body(post);
    }


}
