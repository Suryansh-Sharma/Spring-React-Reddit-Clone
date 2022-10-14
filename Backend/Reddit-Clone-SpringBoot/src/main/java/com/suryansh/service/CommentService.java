package com.suryansh.service;

import com.suryansh.dto.CommentsDto;

import java.util.List;

public interface CommentService {
    void save(CommentsDto commentsDto);

    List<CommentsDto> getAllCommentsForPost(Long postId);

    List<CommentsDto> getCommentsByUser(String username);
}
