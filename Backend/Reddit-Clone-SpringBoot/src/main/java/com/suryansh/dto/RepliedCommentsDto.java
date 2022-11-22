package com.suryansh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepliedCommentsDto {
    private Long id;
    private Long postId;
    private String createdDate;
    private String text;
    private String userName;
    private String repliedTo;
}
