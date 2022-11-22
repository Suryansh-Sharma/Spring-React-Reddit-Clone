package com.suryansh.service;

import com.suryansh.dto.CommentsDto;
import com.suryansh.dto.RepliedCommentsDto;
import com.suryansh.entity.Comment;
import com.suryansh.entity.Post;
import com.suryansh.entity.RepliedComments;
import com.suryansh.entity.User;
import com.suryansh.exception.SpringRedditException;
import com.suryansh.model.RepliedCommentModel;
import com.suryansh.repository.CommentRepository;
import com.suryansh.repository.PostRepository;
import com.suryansh.repository.RepliedCommentsRepository;
import com.suryansh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final RepliedCommentsRepository repliedCommentsRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Transactional
    @Async
    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findByPostId(commentsDto.getPostId()).orElseThrow(() -> new SpringRedditException("No post found found"));
        User user = userRepository.findByUsername(commentsDto.getUserName()).get();

        Comment comment = Comment.builder().text(commentsDto.getText())
                .post(post)
                .createdDate(Instant.now())
                .user(user).repliedComments(null).noOfReplies(0).build();
        try {
            commentRepository.save(comment);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Spring-Reddit-Clone");
            message.setTo(post.getUser().getEmail());
            message.setSubject(user.getUsername() + " Posted a comment");
            message.setText(user.getUsername() + " Posted a comment on Post " + " " + post.getPostName());
            mailSender.send(message);

        } catch (Exception e) {
            throw new SpringRedditException("Unable to save Comment");
        }
    }

    @Override
    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new SpringRedditException("Post Not Found"));

        return commentRepository.findByPost(post).stream().map(this::commentEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<CommentsDto> getCommentsByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("No user found of name : " + username));
        return commentRepository.findAllByUser(user).stream().map(this::commentEntityToDto).collect(Collectors.toList());
    }

    @Transactional
    @Async
    public void saveRepliedComment(RepliedCommentModel repliedCommentDto) {
        Comment commentToUpdate = commentRepository.findById(repliedCommentDto.getCommentId())
                .orElseThrow(() -> new SpringRedditException("No comment found"));
        commentToUpdate.setNoOfReplies(commentToUpdate.getNoOfReplies() + 1);
        commentRepository.save(commentToUpdate);
        RepliedComments repliedComment = RepliedComments.builder().
                commentId(repliedCommentDto.getCommentId())
                .username(repliedCommentDto.getUsername())
                .createdDate(Instant.now())
                .repliedTo(repliedCommentDto.getRepliedTo())
                .text(repliedCommentDto.getText())
                .build();
        try {
            User user = userRepository.findByUsername(repliedCommentDto.getRepliedTo()).get();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Spring-Reddit-Clone");
            message.setTo(user.getEmail());
            message.setSubject(repliedCommentDto.getUsername() + "   " + " Replied to your comment. ");
            message.setText("Hello " + user.getUsername() + " " + " We Inform you that " + repliedCommentDto.getUsername() + " replied to your comment on post " + "  " + repliedCommentDto.getPostId());
            mailSender.send(message);
            repliedCommentsRepository.save(repliedComment);
        } catch (Exception e) {
            throw new SpringRedditException("Unable to save Replied Comment");
        }
    }

    private CommentsDto commentEntityToDto(Comment comment) {
        PrettyTime p = new PrettyTime();
        String getDuration = p.format(comment.getCreatedDate());
        return CommentsDto.builder()
                .Id(comment.getId())
                .postId(comment.getPost().getPostId())
                .createdDate(getDuration)
                .text(comment.getText())
                .userName(comment.getUser().getUsername())
                .noOfReplies(comment.getNoOfReplies())
                .repliedComments(getRepliedComments(comment))
                .build();
    }

    private List<RepliedCommentsDto> getRepliedComments(Comment comment) {
        if (comment.getNoOfReplies() > 0) {
            return comment.getRepliedComments().stream()
                    .map(this::repliedCommentsDto)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public RepliedCommentsDto repliedCommentsDto(RepliedComments repliedComment) {
        PrettyTime p = new PrettyTime();
        String getDuration = p.format(repliedComment.getCreatedDate());
        return RepliedCommentsDto.builder()
                .id(repliedComment.getId())
                .postId(repliedComment.getCommentId())
                .createdDate(getDuration)
                .text(repliedComment.getText())
                .userName(repliedComment.getUsername())
                .repliedTo(repliedComment.getRepliedTo())
                .build();
    }


}
