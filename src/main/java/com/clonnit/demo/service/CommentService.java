package com.clonnit.demo.service;


import com.clonnit.demo.dto.CommentDto;
import com.clonnit.demo.model.Comment;
import com.clonnit.demo.model.Post;
import com.clonnit.demo.model.User;
import com.clonnit.demo.model.Vote;
import com.clonnit.demo.repository.CommentRepository;
import com.clonnit.demo.repository.PostRepository;
import com.clonnit.demo.repository.UserRepository;
import com.clonnit.demo.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final VoteService voteService;
    private final DtoService dtoService;
    private final AuthService authService;

    @Transactional
    public CommentDto saveComment(CommentDto dto) {
        Comment comment = dtoService.mapDtoToComment(dto);

        if (comment.getId() == null) {
            comment.setUser(authService.getActiveUser());
            comment.setVoteCount(0);
        }

        commentRepository.save(comment);
        dto = dtoService.mapCommentToDto(comment);

        return dto;
    }

    @Transactional(readOnly = true)
    public List<CommentDto> listCommentByPost(Integer postId) {
        Optional<Post> post = postRepository.findById(postId);

        return post.map(value ->
                commentRepository
                        .findAllByPost(value)
                        .stream()
                        .map(dtoService::mapCommentToDto)
                        .collect(Collectors.toList())
        ).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> listCommentByUser(Integer userId) {
        Optional<User> user = userRepository.findById(userId);

        return user.map(value ->
                commentRepository
                        .findAllByUser(value)
                        .stream()
                        .map(dtoService::mapCommentToDto)
                        .collect(Collectors.toList())
        ).orElse(null);
    }

    @Transactional(readOnly = true)
    public Comment getComment(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteComment(Comment comment) {
        List<Vote> votes = voteRepository.findAllByComment(comment);
        votes.forEach(voteService::deleteVote);
        List<Comment> comments = commentRepository.findAllByParentComment(comment);
        comments.forEach(this::deleteComment);

        commentRepository.delete(comment);
    }
}
