package com.clonnit.demo.service;

import com.clonnit.demo.model.Comment;
import com.clonnit.demo.model.Post;
import com.clonnit.demo.model.Subclonnit;
import com.clonnit.demo.model.User;
import com.clonnit.demo.repository.CommentRepository;
import com.clonnit.demo.repository.PostRepository;
import com.clonnit.demo.repository.SubclonnitRepository;
import com.clonnit.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ValidationService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final SubclonnitRepository subclonnitRepository;
    private final UserRepository userRepository;

    public Comment getCommentOrNull(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }

    public Post getPostOrNull(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    public Subclonnit getSubclonnitOrNull(Integer id) {
        return subclonnitRepository.findById(id).orElse(null);
    }

    public User getUserOrNull(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
