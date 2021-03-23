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
import com.clonnit.demo.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@Slf4j
public class DtoService {
    private final ValidationService validationService;
    private final CommentRepository commentRepository;

    public CommentDto mapCommentToDto(Comment comment) {
        CommentDto.CommentDtoBuilder dto = CommentDto.builder()
                .id(comment.getId())
                .userId(comment.getUser().getId())
                .userUsername(comment.getUser().getUsername())
                .content(comment.getContent())
                .created(comment.getCreated());

        if (comment.getPost() != null) {
            dto.postId(comment.getPost().getId());
        }

        if (comment.getParentComment() != null) {
            dto.parentCommentId(comment.getParentComment().getId());
        }

        ArrayList<Comment> childComments = getChildComments(comment);
        if (!childComments.isEmpty()) {
            ArrayList<CommentDto> childCommentsDto = new ArrayList<>();
            childComments.forEach(c -> childCommentsDto.add(mapCommentToDto(c)));

            dto.childComments(childCommentsDto);
        }

        return dto.build();

    }

    public Comment mapDtoToComment(CommentDto dto) {
        Comment.CommentBuilder comment = Comment.builder()
                .id(dto.getId())
                .parentComment(validationService.getCommentOrNull(dto.getParentCommentId()))
                .content(dto.getContent());

        if (dto.getPostId() != null) {
            comment.post(validationService.getPostOrNull(dto.getPostId()));
        }

        return comment.build();
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

    private ArrayList<Comment> getChildComments(Comment comment) {
        return new ArrayList<>(commentRepository.findAllByParentComment(comment));
    }
}