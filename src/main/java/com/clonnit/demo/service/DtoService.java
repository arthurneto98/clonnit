package com.clonnit.demo.service;

import com.clonnit.demo.dto.CommentDto;
import com.clonnit.demo.dto.PostDto;
import com.clonnit.demo.dto.SubclonnitDto;
import com.clonnit.demo.dto.VoteDto;
import com.clonnit.demo.exceptions.ClonnitException;
import com.clonnit.demo.model.Comment;
import com.clonnit.demo.model.Post;
import com.clonnit.demo.model.Subclonnit;
import com.clonnit.demo.model.Vote;
import com.clonnit.demo.model.enums.VoteTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class DtoService {
    private final ValidationService validationService;

    public CommentDto mapCommentToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .userUsername(comment.getUser().getUsername())
                .content(comment.getContent())
                .created(comment.getCreated())
                .build();
    }

    public Comment mapDtoToComment(CommentDto dto) {
        return Comment.builder()
                .id(dto.getId())
                .post(validationService.getPostOrNull(dto.getPostId()))
                .content(dto.getContent())
                .build();
    }

    public PostDto mapPostToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .url(post.getUrl())
                .content(post.getContent())
                .voteCount(post.getVoteCount())
                .created(post.getCreated().toString())
                .userId(post.getUser().getId())
                .subclonnitId(post.getSubclonnit().getId())
                .build();
    }

    public Post mapDtoToPost(PostDto dto) {
        return Post.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .url(dto.getUrl())
                .content(dto.getContent())
                .voteCount(dto.getVoteCount())
                .subclonnit(validationService.getSubclonnitOrNull(dto.getSubclonnitId()))
                .build();
    }

    public SubclonnitDto mapSubclonnitToDto(Subclonnit subclonnit) {
        return SubclonnitDto.builder()
                .id(subclonnit.getId())
                .name(subclonnit.getName())
                .description(subclonnit.getDescription())
                .postNumber(subclonnit.getPostList().size())
                .build();
    }

    public Subclonnit mapDtoToSubclonnit(SubclonnitDto dto) {
        return Subclonnit.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public VoteDto mapVoteToDto(Vote vote) {
        VoteDto.VoteDtoBuilder dto = VoteDto.builder()
                .id(vote.getId())
                .voteType(vote.getVoteType().toString())
                .userId(vote.getUser().getId());

        if (vote.getPost() != null) {
            dto.postId(vote.getPost().getId());
        } else if (vote.getComment() != null) {
            dto.commentId(vote.getComment().getId());
        } else {
            throw new ClonnitException("Voto sem publicação");
        }

        return dto.build();
    }

    public Vote mapDtoToVote(VoteDto dto) {
        Vote.VoteBuilder vote = Vote.builder()
                .id(dto.getId())
                .voteType(VoteTypeEnum.valueOf(dto.getVoteType()))
                .user(validationService.getUserOrNull(dto.getUserId()));

        if (dto.getPostId() != null) {
            vote.post(validationService.getPostOrNull(dto.getPostId()));
        } else if (dto.getCommentId() != null) {
            vote.comment(validationService.getCommentOrNull(dto.getCommentId()));
        } else {
            throw new ClonnitException("Voto sem publicação");
        }

        return vote.build();
    }
}