package com.clonnit.demo.service;

import com.clonnit.demo.dto.PostDto;
import com.clonnit.demo.dto.SubclonnitDto;
import com.clonnit.demo.dto.VoteDto;
import com.clonnit.demo.model.Post;
import com.clonnit.demo.model.Subclonnit;
import com.clonnit.demo.model.User;
import com.clonnit.demo.model.Vote;
import com.clonnit.demo.model.enums.VoteTypeEnum;
import com.clonnit.demo.repository.PostRepository;
import com.clonnit.demo.repository.SubclonnitRepository;
import com.clonnit.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class DtoService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubclonnitRepository subclonnitRepository;

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
                .post(getPostOrNull(dto.getPostId()))
                .user(getUserOrNull(dto.getUserId()))
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
                .user(getUserOrNull(dto.getUserId()))
                .subclonnit(getSubclonnitOrNull(dto.getSubClonnitId()))
                .build();
    }

    //TODO migrar
    private Post getPostOrNull(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }

    private User getUserOrNull(Integer id) {
        Optional<User> post = userRepository.findById(id);
        return post.orElse(null);
    }

    private Subclonnit getSubclonnitOrNull(Integer id) {
        Optional<Subclonnit> post = subclonnitRepository.findById(id);
        return post.orElse(null);
    }
}