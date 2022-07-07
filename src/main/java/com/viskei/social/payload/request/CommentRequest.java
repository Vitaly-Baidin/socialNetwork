package com.viskei.social.payload.request;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
public class CommentRequest {

    @DecimalMin("1")
    private Long id;

    private String username;

    private String text;

}
