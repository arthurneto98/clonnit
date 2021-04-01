package com.clonnit.demo.controller;

import com.clonnit.demo.dto.PostDto;
import com.clonnit.demo.exceptions.ClonnitException;
import com.clonnit.demo.model.Post;
import com.clonnit.demo.model.User;
import com.clonnit.demo.service.AuthService;
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
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody PostDto post) {
        User user = authService.getActiveUser();
        Boolean isUpdate = post.getId() != null;

        if (isUpdate && !post.getUserId().equals(user.getId())) {
            throw new ClonnitException("Não é permitido editar o post de outro usuário");
        }


        HttpStatus status = isUpdate ? HttpStatus.OK : HttpStatus.CREATED;
        return ResponseEntity
                .status(status)
                .body(postService.savePost(post));
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
        PostDto dto = postService.getPostDto(id);
        return dto != null ?
                ResponseEntity.status(HttpStatus.OK).body(dto) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/p/{url}")
    public ResponseEntity<PostDto> getByUrl(@PathVariable String url) {
        PostDto dto = postService.getPostByUrl(url);
        return dto != null ?
                ResponseEntity.status(HttpStatus.OK).body(dto) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        User user = authService.getActiveUser();
        Post post = postService.getPost(id);

        if (post == null) {
            throw new ClonnitException("Post não encontrado");
        } else if (post.getUser() != user) {
            throw new ClonnitException("Não é permitido alterar o post de outro usuário");
        }

        postService.deletePost(post);
        return ResponseEntity.status(HttpStatus.OK).body("Post " + id + "removido com sucesso");
    }
}
