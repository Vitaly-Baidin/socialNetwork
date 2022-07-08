package com.viskei.social.payload.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class CommentRequest {

    @Min(1)
    private Long id;

    private String username;

    private String text;

}
