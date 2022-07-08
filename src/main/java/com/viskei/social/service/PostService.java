package com.viskei.social.service;

import com.viskei.social.payload.request.PostRequest;
import com.viskei.social.payload.response.PostResponse;

import java.util.List;

public interface PostService {

    PostResponse createPost(PostRequest postRequest);

    PostResponse createComment(PostRequest postRequest);

    PostResponse getPost(PostRequest postRequest);

    List<PostResponse> getAllPostsByTags(PostRequest postRequest);

    PostResponse getAllCommentsByPost(PostRequest postRequest);

    PostResponse updatePost(PostRequest postRequest);

    PostResponse deletePost(PostRequest postRequest);

}
