package com.suryansh.service;

import com.suryansh.dto.CommentsDto;
import com.suryansh.model.RepliedCommentModel;

import java.util.List;

public interface CommentService {
    void save(CommentsDto commentsDto);

    List<CommentsDto> getAllCommentsForPost(Long postId);

    List<CommentsDto> getCommentsByUser(String username);

    void saveRepliedComment(RepliedCommentModel repliedCommentDto);
}
