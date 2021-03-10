package com.clonnit.demo.repository;

import com.clonnit.demo.model.Comment;
import com.clonnit.demo.model.Post;
import com.clonnit.demo.model.User;
import com.clonnit.demo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Optional<Vote> findByPostAndUser(Post post, User user);
    Optional<Vote> findByCommentAndUser(Comment comment, User user);
}