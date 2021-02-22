package com.clonnit.demo.service;

import com.clonnit.demo.dto.PostDto;
import com.clonnit.demo.dto.SubclonnitDto;
import com.clonnit.demo.dto.VoteDto;
import com.clonnit.demo.model.Post;
import com.clonnit.demo.model.Subclonnit;
import com.clonnit.demo.model.Vote;
import com.clonnit.demo.model.enums.VoteTypeEnum;

import java.time.LocalDateTime;

public class DtoService {
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
                .userId(vote.getUser().getId())
                .build();
    }

    public Vote mapDtoToVote(VoteDto dto) {
        return Vote.builder()
                .voteType(VoteTypeEnum.valueOf(dto.getVoteType()))
                //get post by id
                //get user by id
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
                //get user by id
                //get subclonnit by id
                .build();
    }
}
