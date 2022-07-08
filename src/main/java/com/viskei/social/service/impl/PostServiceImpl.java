package com.viskei.social.service.impl;

import com.viskei.social.exception.PostNotFoundException;
import com.viskei.social.models.Comment;
import com.viskei.social.models.Post;
import com.viskei.social.payload.request.PostRequest;
import com.viskei.social.payload.response.PostResponse;
import com.viskei.social.repository.PostRepository;
import com.viskei.social.service.CommentService;
import com.viskei.social.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final CommentService commentService;
    private final PostRepository postRepository;

    public PostServiceImpl(CommentService commentService,
                           PostRepository postRepository) {
        this.commentService = commentService;
        this.postRepository = postRepository;
    }

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        Post post = new Post();

        if (!StringUtils.hasText(postRequest.getTitle()))
            throw new IllegalArgumentException("ERROR: title not be empty or null!");

        if (!StringUtils.hasText(postRequest.getText()))
            throw new IllegalArgumentException("ERROR: text not be empty or null!");

        post.setTitle(postRequest.getTitle());
        post.setTags(postRequest.getTags());
        post.setText(postRequest.getText());

        postRepository.save(post);

        return new PostResponse(post);
    }

    @Override
    public PostResponse createComment(PostRequest postRequest) {
        if (postRequest.getComment() == null)
            throw new IllegalArgumentException("ERROR: comment not be null!");

        Post post = validPostRequestById(postRequest);

        post.addComment(commentService.createComment(postRequest.getComment()));

        postRepository.save(post);

        return new PostResponse(post);
    }

    @Override
    public PostResponse getPost(PostRequest postRequest) {
        Post post = validPostRequestById(postRequest);

        return new PostResponse(post);
    }

    @Override
    public List<PostResponse> getAllPostsByTags(PostRequest postRequest) {
        if (postRequest == null || CollectionUtils.isEmpty(postRequest.getTags()))
            return postRepository.findAll().stream()
                    .map(PostResponse::new)
                    .collect(Collectors.toList());

        List<String> tags = postRequest.getTags();

        return postRepository.findAll().stream()
                .filter(e -> CollectionUtils.containsAny(e.getTags(), tags))
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponse getAllCommentsByPost(PostRequest postRequest) {
        Post post = validPostRequestById(postRequest);

        PostResponse postResponse = new PostResponse();
        postResponse.setComments(post.getComments());

        return postResponse;
    }

    @Override
    public PostResponse updatePost(PostRequest postRequest) {
        Post post = validPostRequestById(postRequest);

        if (StringUtils.hasText(postRequest.getTitle())) {
            post.setTitle(postRequest.getTitle());
        }

        if (StringUtils.hasText(postRequest.getText())) {
            post.setText(postRequest.getText());
        }

        if (postRequest.getTags() != null) {
            post.setTags(postRequest.getTags());
        }

        postRepository.save(post);

        return new PostResponse(post);
    }

    @Override
    public PostResponse deletePost(PostRequest postRequest) {
        Post post = validPostRequestById(postRequest);

        PostResponse postResponse = new PostResponse(post);

        postRepository.delete(post);

        return postResponse;
    }

    private Post validPostRequestById(PostRequest postRequest) {
        if (postRequest.getId() == null)
            throw new IllegalArgumentException("ERROR: id not be empty or null!");

        return postRepository.findById(postRequest.getId())
                .orElseThrow(() -> new PostNotFoundException("ERROR: Post is not found!"));
    }

}
