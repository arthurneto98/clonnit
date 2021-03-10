package com.clonnit.demo.service;

import com.clonnit.demo.dto.CommentDto;
import com.clonnit.demo.dto.PostDto;
import com.clonnit.demo.dto.SubclonnitDto;
import com.clonnit.demo.dto.VoteDto;
import com.clonnit.demo.model.Comment;
import com.clonnit.demo.model.Post;
import com.clonnit.demo.model.Subclonnit;
import com.clonnit.demo.model.Vote;
import com.clonnit.demo.model.enums.VoteTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class DtoService {
    private final PostService postService;
    private final UserService userService;
    private final SubclonnitService subclonnitService;
    private final CommentService commentService;

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
                .post(postService.getPostOrNull(dto.getPostId()))
                .user(userService.getUserOrNull(dto.getUserId()))
                .content(dto.getContent())
                .created(dto.getCreated())
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
                .subClonnitId(post.getSubclonnit().getId())
                .build();
    }

    public Post mapDtoToPost(PostDto dto) {
        return Post.builder()
                .title(dto.getTitle())
                .url(dto.getUrl())
                .content(dto.getContent())
                .voteCount(dto.getVoteCount())
                .created(LocalDateTime.parse(dto.getCreated()))
                .user(userService.getUserOrNull(dto.getUserId()))
                .subclonnit(subclonnitService.getSubclonnitOrNull(dto.getSubClonnitId()))
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
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public VoteDto mapVoteToDto(Vote vote) {
        return VoteDto.builder()
                .id(vote.getId())
                .voteType(vote.getVoteType().toString())
                .postId(vote.getPost().getId())
                .commentId(vote.getComment().getId())
                .userId(vote.getUser().getId())
                .build();
    }

    public Vote mapDtoToVote(VoteDto dto) {
        return Vote.builder()
                .voteType(VoteTypeEnum.valueOf(dto.getVoteType()))
                .post(postService.getPostOrNull(dto.getPostId()))
                .comment(commentService.getCommentOrNull(dto.getCommentId()))
                .user(userService.getUserOrNull(dto.getUserId()))
                .build();
    }
}