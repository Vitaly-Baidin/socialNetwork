package com.viskei.social.payload.response;

import com.viskei.social.models.Comment;
import lombok.Data;

@Data
public class CommentResponse {

    private Long id;

    private String username;

    private String text;

    public CommentResponse() {
    }

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.text = comment.getText();
    }

}
