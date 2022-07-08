package com.viskei.social.service.impl;

import com.viskei.social.exception.CommentNotFoundException;
import com.viskei.social.models.Comment;
import com.viskei.social.payload.request.CommentRequest;
import com.viskei.social.payload.response.CommentResponse;
import com.viskei.social.repository.CommentRepository;
import com.viskei.social.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        if (!StringUtils.hasText(commentRequest.getUsername()))
            throw new IllegalArgumentException("ERROR: username not be empty or null!");

        if (!StringUtils.hasText(commentRequest.getText()))
            throw new IllegalArgumentException("ERROR: text not be empty or null!");

        comment.setUsername(commentRequest.getUsername());
        comment.setText(commentRequest.getText());

        commentRepository.save(comment);

        return comment;
    }

    @Override
    public CommentResponse getComment(CommentRequest commentRequest) {
        return new CommentResponse(
                commentRepository.findById(commentRequest.getId())
                        .orElseThrow(() ->
                                new CommentNotFoundException("ERROR: Comment is not found!")));
    }

    @Override
    public List<CommentResponse> getAllCommentByUsername(CommentRequest commentRequest) {
        List<CommentResponse> comments = new ArrayList<>();

        for (Comment comment : commentRepository.findAllByUsername(commentRequest.getUsername())) {
            comments.add(new CommentResponse(comment));
        }

        return comments;
    }

    @Override
    public CommentResponse updateComment(CommentRequest commentRequest) {
        if (commentRequest.getId() == null) throw new IllegalArgumentException("ERROR: id not be empty or null!");
        Comment comment = commentRepository.findById(commentRequest.getId())
                .orElseThrow(() ->
                        new CommentNotFoundException("ERROR: Comment is not found!"));

        if (StringUtils.hasText(commentRequest.getText())) {
            comment.setText(commentRequest.getText());
            commentRepository.save(comment);
        }

        return new CommentResponse(comment);
    }

    @Override
    public CommentResponse deleteComment(CommentRequest commentRequest) {
        if (commentRequest.getId() == null) throw new IllegalArgumentException("The given id must not be null!");
        Comment comment = commentRepository.findById(commentRequest.getId())
                .orElseThrow(() ->
                        new CommentNotFoundException("ERROR: Comment is not found!"));

        commentRepository.delete(comment);

        return new CommentResponse(comment);
    }
}
