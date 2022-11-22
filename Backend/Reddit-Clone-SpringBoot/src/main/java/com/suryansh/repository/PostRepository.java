package com.suryansh.repository;

import com.suryansh.entity.Post;
import com.suryansh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);

    Optional<Post> findByPostId(Long postId);

    @Query(nativeQuery = true, value = "SELECT * FROM post ORDER BY rand() LIMIT 5")
    List<Post> getRandomPost();
}