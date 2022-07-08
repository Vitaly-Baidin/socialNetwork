package com.viskei.social.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class PostRequest {

    private Long id;

    private String title;

    private List<String> tags;

    private String text;

    private CommentRequest comment;

}
