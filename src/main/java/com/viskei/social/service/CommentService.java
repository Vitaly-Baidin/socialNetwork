package com.viskei.social.service;

import com.viskei.social.models.Comment;
import com.viskei.social.payload.request.CommentRequest;
import com.viskei.social.payload.response.CommentResponse;

import java.util.List;

public interface CommentService {

    Comment createComment(CommentRequest commentRequest);

    CommentResponse getComment(CommentRequest commentRequest);

    List<CommentResponse> getAllCommentByUsername(CommentRequest commentRequest);

    CommentResponse updateComment(CommentRequest commentRequest);

    CommentResponse deleteComment(CommentRequest commentRequest);
}
