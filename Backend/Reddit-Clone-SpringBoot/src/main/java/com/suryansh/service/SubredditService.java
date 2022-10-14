package com.suryansh.service;

import com.suryansh.model.SubredditModel;

import java.util.List;

public interface SubredditService {
    SubredditModel save(SubredditModel subredditModel);

    List<SubredditModel> getAll();

    SubredditModel getById(Long id);
}
