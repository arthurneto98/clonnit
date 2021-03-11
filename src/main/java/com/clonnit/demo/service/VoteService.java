package com.clonnit.demo.service;

import com.clonnit.demo.dto.VoteDto;
import com.clonnit.demo.exceptions.ClonnitException;
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
    private final ValidationService validationService;
    private final VoteRepository voteRepository;

    @Transactional
    public VoteDto saveVote(VoteDto dto) {
        Vote vote = dtoService.mapDtoToVote(dto);
        vote.setUser(authService.getActiveUser());

        voteRepository.save(vote);

        Post post = vote.getPost();
        Comment comment = vote.getComment();
        if(post != null) {
            post.setVoteCount(post.getVoteCount() + vote.getVoteType().getValue());
//        } else if (comment != null) {
//            comment.setVoteCount(comment.getVoteCount() + vote.getVoteType().getValue());
        } else {
            throw new ClonnitException("Voto sem publicação");
        }

        dto = dtoService.mapVoteToDto(vote);

        return dto;
    }

    @Transactional(readOnly = true)
    public VoteDto getVoteByPost(Integer postId, Integer userId) {
        Post post = validationService.getPostOrNull(postId);
        User user = validationService.getUserOrNull(userId);

        Optional<Vote> vote = voteRepository.findByPostAndUser(post, user);

        return vote.map(dtoService::mapVoteToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public VoteDto getVoteByComment(Integer commentId, Integer userId) {
        Comment comment = validationService.getCommentOrNull(commentId);
        User user = validationService.getUserOrNull(userId);

        Optional<Vote> vote = voteRepository.findByCommentAndUser(comment, user);

        return vote.map(dtoService::mapVoteToDto).orElse(null);
    }
}
