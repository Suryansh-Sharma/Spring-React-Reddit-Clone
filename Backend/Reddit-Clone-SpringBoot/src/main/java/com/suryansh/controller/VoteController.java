package com.suryansh.controller;

import com.suryansh.dto.VoteDto;
import com.suryansh.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;
    private VoteController (VoteService voteService){
        this.voteService=voteService;
    }

    @PostMapping
    public ResponseEntity<Void>vote(@RequestBody VoteDto voteDto){
        voteService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
