package com.suryansh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsDto {
    private Long Id;
    private Long postId;
    private String createdDate;
    private String text;
    private String userName;
    private int noOfReplies;
    private List<RepliedCommentsDto> repliedComments;
}
