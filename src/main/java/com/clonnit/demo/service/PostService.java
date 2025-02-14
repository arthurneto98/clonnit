package com.clonnit.demo.service;

import com.clonnit.demo.dto.PostDto;
import com.clonnit.demo.model.*;
import com.clonnit.demo.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final SubclonnitRepository subclonnitRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;
    private final VoteService voteService;
    private final CommentService commentService;
    private final DtoService dtoService;
    private final AuthService authService;

    @Transactional
    public PostDto savePost(PostDto dto) {
        Post post = dtoService.mapDtoToPost(dto);

        if (post.getId() == null) {
            post.setUser(authService.getActiveUser());
            post.setCreated(LocalDateTime.now());
            post.setVoteCount(0);

            String title = normalize(dto.getTitle().toLowerCase());
            if (title.length() > 30) {
                title = title.substring(0, 30);
            }
            title = title.replace(" ", "-");
            String url = UUID.randomUUID().toString().substring(0, 6).concat("-").concat(title);
            post.setUrl(url);
        }

        postRepository.save(post);
        dto = dtoService.mapPostToDto(post);

        return dto;
    }

    @Transactional(readOnly = true)
    public List<PostDto> listPost() {
        return postRepository.findAll().stream().map(dtoService::mapPostToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> listPostBySubclonnit(String name) {
        Optional<Subclonnit> subclonnit = subclonnitRepository.findByName(name);

        return subclonnit.map(value ->
                postRepository.findAllBySubclonnit(value)
                        .stream().map(dtoService::mapPostToDto).collect(Collectors.toList())
        ).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PostDto> listPostByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        return user.map(value ->
                postRepository.findAllByUser(value)
                        .stream().map(dtoService::mapPostToDto).collect(Collectors.toList())
        ).orElse(null);
    }

    @Transactional(readOnly = true)
    public Post getPost(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public PostDto getPostDto(Integer id) {
        return postRepository.findById(id).map(dtoService::mapPostToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public PostDto getPostByUrl(String url) {
        Optional<Post> post = postRepository.findByUrl(url);
        return post.map(dtoService::mapPostToDto).orElse(null);
    }

    @Transactional
    public void deletePost(Post post) {
        List<Vote> votes = voteRepository.findAllByPost(post);
        votes.forEach(voteService::deleteVote);
        List<Comment> comments = commentRepository.findAllByPost(post);
        comments.forEach(commentService::deleteComment);

        postRepository.delete(post);
    }

    private String normalize(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        s = s.replaceAll("[^a-zA-Z0-9\\s]", "");
        return s;
    }
}