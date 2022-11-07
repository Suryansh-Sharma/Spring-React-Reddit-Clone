package com.suryansh.controller;

import com.suryansh.dto.PostRequest;
import com.suryansh.exception.SpringRedditException;
import com.suryansh.model.PostResponse;
import com.suryansh.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequest postRequest){
        try {
            postService.save(postRequest);
            return new ResponseEntity<>("Post Saved !!",HttpStatus.CREATED);
        }catch (SpringRedditException e){
            return new ResponseEntity<>("Unable to Save Post",HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>>getAllPost(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getAllPost());
    }

    @GetMapping("by-user/{username}")
    public ResponseEntity<List<PostResponse>> getPostByUserName(@PathVariable String username){
        try{
            return new ResponseEntity<>(postService.getPostByUsername(username)
                    ,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id){
        try{
            return new ResponseEntity<>(postService.getPostById(id)
                    ,HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
