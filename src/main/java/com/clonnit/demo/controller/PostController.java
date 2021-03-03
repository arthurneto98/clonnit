package com.clonnit.demo.controller;

import com.clonnit.demo.dto.PostDto;
import com.clonnit.demo.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody PostDto post) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.savePost(post));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> list() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.listPost());
    }

    @GetMapping("/c/{subclonnit}")
    public ResponseEntity<List<PostDto>> listBySubclonnit(@PathVariable String subclonnit) {
        List<PostDto> postList = postService.listPostBySubclonnit(subclonnit);

        if (postList == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postList);
    }

    @GetMapping("/u/{user}")
    public ResponseEntity<List<PostDto>> listByUsername(@PathVariable String user) {
        List<PostDto> postList = postService.listPostByUsername(user);

        if (postList == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Integer id) {
        PostDto dto = postService.getPost(id);
        return dto != null ?
                ResponseEntity.status(HttpStatus.OK).body(dto) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
