package com.clonnit.demo.service;

import com.clonnit.demo.dto.VoteDto;
import com.clonnit.demo.model.Comment;
import com.clonnit.demo.model.Post;
import com.clonnit.demo.model.User;
import com.clonnit.demo.model.Vote;
import com.clonnit.demo.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {
    private final DtoService dtoService;
    private final AuthService authService;
    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final VoteRepository voteRepository;

    @Transactional
    public VoteDto saveVote(VoteDto dto) {
        Vote vote = dtoService.mapDtoToVote(dto);
        vote.setUser(authService.getActiveUser());

        voteRepository.save(vote);
        dto.setId(vote.getId());

        return dto;
    }

    @Transactional(readOnly = true)
    public VoteDto getVoteByPost(Integer postId, Integer userId) {
        Post post = postService.getPostOrNull(postId);
        User user = userService.getUserOrNull(userId);

        Optional<Vote> vote = voteRepository.findByPostAndUser(post, user);

        return vote.map(dtoService::mapVoteToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public VoteDto getVoteByComment(Integer commentId, Integer userId) {
        Comment comment = commentService.getCommentOrNull(commentId);
        User user = userService.getUserOrNull(userId);

        Optional<Vote> vote = voteRepository.findByCommentAndUser(comment, user);

        return vote.map(dtoService::mapVoteToDto).orElse(null);
    }
}
