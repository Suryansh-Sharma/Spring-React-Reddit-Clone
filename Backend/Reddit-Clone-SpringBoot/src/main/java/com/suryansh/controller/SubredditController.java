package com.suryansh.controller;

import com.suryansh.exception.SpringRedditException;
import com.suryansh.model.SubredditModel;
import com.suryansh.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@CrossOrigin
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditModel> createSubreddit(@RequestBody SubredditModel subredditModel) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(subredditService.save(subredditModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<SubredditModel>> getAllSubReddit() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditModel> getSubRedditById(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(subredditService.getById(id));
        } catch (SpringRedditException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("by-name/{name}")
    public ResponseEntity<SubredditModel> getSubredditByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditService.getByName(name));
    }
}
