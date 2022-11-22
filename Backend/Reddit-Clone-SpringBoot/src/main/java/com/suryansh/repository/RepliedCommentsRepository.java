package com.suryansh.repository;

import com.suryansh.entity.RepliedComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepliedCommentsRepository extends JpaRepository<RepliedComments, Long> {
}