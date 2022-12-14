package com.suryansh.service;

import com.suryansh.dto.PostRequest;
import com.suryansh.model.PostResponse;

import java.util.List;

public interface PostService {
    void save(PostRequest postRequest);

    List<PostResponse> getAllPost();

    List<PostResponse> getPostByUsername(String username);

    PostResponse getPostById(Long postId);

    List<PostResponse> getRandomPost();
}
