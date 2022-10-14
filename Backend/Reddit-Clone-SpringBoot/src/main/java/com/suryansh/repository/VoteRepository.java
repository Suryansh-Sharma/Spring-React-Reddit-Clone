package com.suryansh.repository;

import com.suryansh.entity.Post;
import com.suryansh.entity.User;
import com.suryansh.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

    Vote findByPost(Post post);
}