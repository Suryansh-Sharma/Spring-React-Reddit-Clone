package com.suryansh.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepliedCommentModel {
    private Long commentId;
    private String username;
    private String repliedTo;
    private String text;
    private Long postId;
}
