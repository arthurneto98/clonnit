package com.clonnit.demo.service;

import com.clonnit.demo.dto.PostDto;
import com.clonnit.demo.model.Post;
import com.clonnit.demo.model.Subclonnit;
import com.clonnit.demo.model.User;
import com.clonnit.demo.repository.PostRepository;
import com.clonnit.demo.repository.SubclonnitRepository;
import com.clonnit.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final SubclonnitRepository subclonnitRepository;
    private final UserRepository userRepository;
    private final DtoService dtoService;
    private final AuthService authService;

    @Transactional
    public PostDto savePost(PostDto dto) {
        //TODO verificar name duplicado

        Post post = dtoService.mapDtoToPost(dto);
        post.setUser(authService.getActiveUser());
        post.setCreated(LocalDateTime.now());
        post.setVoteCount(0);

        String url = dto.getTitle();
        //TODO set url

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
    public PostDto getPost(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(dtoService::mapPostToDto).orElse(null);
    }
}
