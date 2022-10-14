package com.suryansh.service;

import com.suryansh.dto.CommentsDto;
import com.suryansh.dto.NotificationEmail;
import com.suryansh.entity.Comment;
import com.suryansh.entity.Post;
import com.suryansh.entity.User;
import com.suryansh.exception.SpringRedditException;
import com.suryansh.repository.CommentRepository;
import com.suryansh.repository.PostRepository;
import com.suryansh.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final PostRepository postRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    @Override
    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findByPostId(commentsDto.getPostId())
                .orElseThrow(() -> new SpringRedditException("No post found found"));
        User user = authService.getCurrentUser();

        Comment comment = Comment.builder()
                .text(commentsDto.getText())
                .post(post)
                .createdDate(Instant.now())
                .user(user)
                .build();
        try {
            commentRepository.save(comment);
            String message = mailContentBuilder.build(post.getUser().getUsername()
                    + "Posted a comment on your post");
            sendCommentNotification(message,post.getUser());
        }catch (Exception e){
            throw new SpringRedditException("Unable to save Comment");
        }
    }

    @Override
    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new SpringRedditException("Post Not Found"));

        return commentRepository.findByPost(post)
                .stream()
                .map(this::commentEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentsDto> getCommentsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new SpringRedditException("No user found of name : " + username));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(this::commentEntityToDto)
                .collect(Collectors.toList());
    }

    private CommentsDto commentEntityToDto(Comment comment) {
        return CommentsDto.builder()
                .Id(comment.getId())
                .postId(comment.getPost().getPostId())
                .createdDate(comment.getCreatedDate())
                .text(comment.getText())
                .userName(comment.getUser().getUsername())
                .createdDate(comment.getCreatedDate())
                .build();
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername()+"Commented on your post",
                user.getEmail(), message));
    }
}
