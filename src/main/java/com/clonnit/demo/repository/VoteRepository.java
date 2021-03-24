package com.clonnit.demo.repository;

import com.clonnit.demo.model.Comment;
import com.clonnit.demo.model.Post;
import com.clonnit.demo.model.User;
import com.clonnit.demo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findAllByPost(Post post);
    Optional<Vote> findByPostAndUser(Post post, User user);
    List<Vote> findAllByComment(Comment comment);
    Optional<Vote> findByCommentAndUser(Comment comment, User user);
}