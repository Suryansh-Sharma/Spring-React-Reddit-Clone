package com.suryansh.service;

import com.suryansh.dto.PostRequest;
import com.suryansh.entity.*;
import com.suryansh.exception.SpringRedditException;
import com.suryansh.exception.SubredditNotFoundException;
import com.suryansh.model.PostResponse;
import com.suryansh.repository.*;
import lombok.RequiredArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.suryansh.entity.VoteType.DOWNVOTE;
import static com.suryansh.entity.VoteType.UPVOTE;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;

    @Override
    public void save(PostRequest postRequest) {

        User currentUser = authService.getCurrentUser();
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(()-> new SubredditNotFoundException(postRequest.getSubredditName()));
        Post post = Post.builder()
                .postId(postRequest.getPostId())
                .postName(postRequest.getPostName())
                .description(postRequest.getDescription())
                .url(postRequest.getUrl())
                .user(currentUser)
                .createdDate(Instant.now())
                .subreddit(subreddit)
                .voteCount(0)
                .build();
        postRepository.save(post);
    }

    @Override
    public List<PostResponse> getAllPost() {
        return postRepository.findAll()
                .stream()
                .map(this::PostEntityToPostResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getPostByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Sorry user not found of name : " + username));

        List<Post> res = postRepository.findByUser(user);
        if (res.isEmpty())throw  new SpringRedditException("No post found for use : " + username);

        return res.stream()
                .map(this::PostEntityToPostResponse)
                .collect(Collectors.toList());

    }

    @Override
    public PostResponse getPostById(Long postId) {
        Post res = postRepository.findByPostId(postId)
                .orElseThrow(()->new SpringRedditException("Sorry no post found of Id " + postId));
        return PostEntityToPostResponse(res);
    }

    private PostResponse PostEntityToPostResponse(Post post) {
        int commentCount = commentRepository.findByPost(post).size();
        PrettyTime p = new PrettyTime();
        String getDuration = p.format(post.getCreatedDate());
        boolean isPostUpVoted =  checkVoteType(post, UPVOTE);
        boolean isPostDownVoted =  checkVoteType(post, DOWNVOTE);
        return PostResponse.builder()
                .id(post.getPostId())
                .postName(post.getPostName())
                .url(post.getUrl())
                .commentCount(commentCount)
                .description(post.getDescription())
                .userName(post.getUser().getUsername())
                .subredditName(post.getSubreddit().getName())
                .duration(getDuration)
                .voteCount(post.getVoteCount())
                .upVote(isPostUpVoted)
                .downVote(isPostDownVoted)
                .build();
    }
    private boolean checkVoteType(Post post, VoteType voteType){
        Optional<Vote> voteForPostByUser =
                voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                        authService.getCurrentUser());
        return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                .isPresent();
    }
}
