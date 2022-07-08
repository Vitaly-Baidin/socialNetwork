package com.viskei.social.payload.response;

import com.viskei.social.models.Comment;
import com.viskei.social.models.Post;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PostResponse {

    private Long id;

    @NotEmpty
    private String title;

    private List<String> tags;

    @NotEmpty
    private String text;

    private List<Comment> comments;

    public PostResponse() {
    }

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.tags = post.getTags();
        this.text = post.getText();
        this.comments = post.getComments();
    }
}
