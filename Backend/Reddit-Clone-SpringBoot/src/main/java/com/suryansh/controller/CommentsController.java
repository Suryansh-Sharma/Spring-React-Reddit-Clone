package com.suryansh.controller;

import com.suryansh.dto.CommentsDto;
import com.suryansh.model.RepliedCommentModel;
import com.suryansh.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/comments/")
@CrossOrigin
public class CommentsController {

    private final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }


    @Async
    @PostMapping("/save")
    public CompletableFuture<ResponseEntity<String>> CreateComment(@RequestBody CommentsDto commentsDto) {
        try {
            commentService.save(commentsDto);
            return CompletableFuture.completedFuture(new ResponseEntity<>("Comment Saved Successfully"
                    , HttpStatus.OK));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new ResponseEntity<>("Something Went Wrong",
                    HttpStatus.CONFLICT));
        }
    }

    @PostMapping("/saveRepliedComment")
    @Async
    public CompletableFuture<ResponseEntity<String>> saveRepliedComment(@RequestBody RepliedCommentModel
                                                                                repliedCommentDto) {
        try {
            commentService.saveRepliedComment(repliedCommentDto);
            return CompletableFuture.completedFuture(new ResponseEntity<>("Comment Saved Successfully",
                    HttpStatus.OK));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new ResponseEntity<>("Unable to Save Comment !!",
                    HttpStatus.CONFLICT));
        }
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
        try {
            return new ResponseEntity<>(commentService.getAllCommentsForPost(postId),
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentsDto>>getCommentsForUser(@PathVariable String username){
        try {
            return new ResponseEntity<>(commentService.getCommentsByUser(username),
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
