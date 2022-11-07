package com.suryansh.controller;

import com.suryansh.dto.CommentsDto;
import com.suryansh.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
public class CommentsController {

    private final CommentService commentService;

    private CommentsController(CommentService commentService){
        this.commentService=commentService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> CreateComment(@RequestBody CommentsDto commentsDto){
        try {
            commentService.save(commentsDto);
            return new ResponseEntity<>("Comment Saved Successfully",HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Something Went Wrong",HttpStatus.CONFLICT);
        }
    }
    
    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>>getAllCommentsForPost(@PathVariable Long postId){
        try{
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
