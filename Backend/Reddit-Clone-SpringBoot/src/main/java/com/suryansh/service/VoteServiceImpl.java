package com.suryansh.service;

import com.suryansh.dto.VoteDto;
import com.suryansh.entity.Post;
import com.suryansh.entity.User;
import com.suryansh.entity.Vote;
import com.suryansh.exception.SpringRedditException;
import com.suryansh.repository.PostRepository;
import com.suryansh.repository.UserRepository;
import com.suryansh.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.suryansh.entity.VoteType.UPVOTE;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new SpringRedditException("Post Not Found with ID - " + voteDto.getPostId()));
        User user = userRepository.findByUsername(voteDto.getUsername())
                .orElseThrow(()->new SpringRedditException("Unable to find User inside Vote"));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,user);
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post ,user));
        postRepository.save(post);
    }
    private Vote mapToVote(VoteDto voteDto, Post post,User user) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(user)
                .build();
    }
}
