package com.suryansh.service;

import com.suryansh.entity.Subreddit;
import com.suryansh.exception.SpringRedditException;
import com.suryansh.model.SubredditModel;
import com.suryansh.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditImpl implements SubredditService{

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditModel save(SubredditModel subredditModel){
        Subreddit save = subredditRepository.save(mapSubredditDto(subredditModel));
        subredditModel.setId(save.getId());
        return subredditModel;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubredditModel> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    @Override
    public SubredditModel getById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No Subreddit found of Id : " + id));
        return SubredditModel.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPost(0).build();

    }

    @Override
    public SubredditModel getByName(String name) {
        Subreddit subreddit = subredditRepository.findByName(name)
                .orElseThrow(() -> new SpringRedditException("No subreddit found"));
        return SubredditModel.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .build();
    }

    private SubredditModel mapToModel(Subreddit subreddit) {
        return SubredditModel.builder().name(subreddit.getName())
                .id(subreddit.getId())
                .numberOfPost(subreddit.getPosts().size())
                .build();
    }

    private Subreddit mapSubredditDto(SubredditModel subredditModel) {
        return Subreddit.builder().name(subredditModel.getName())
                .description(subredditModel.getDescription())
                .build();
    }
}
