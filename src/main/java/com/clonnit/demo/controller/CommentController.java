package com.clonnit.demo.controller;

import com.clonnit.demo.dto.CommentDto;
import com.clonnit.demo.exceptions.ClonnitException;
import com.clonnit.demo.model.Comment;
import com.clonnit.demo.model.User;
import com.clonnit.demo.service.AuthService;
import com.clonnit.demo.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto comment) {
        HttpStatus status = comment.getId() == null ? HttpStatus.CREATED : HttpStatus.OK;
        //todo verificar se o user logado é o editando

        return ResponseEntity
                .status(status)
                .body(commentService.saveComment(comment));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> listByPost(@PathVariable Integer postId) {
        List<CommentDto> commentList = commentService.listCommentByPost(postId);

        if (commentList == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> listByUser(@PathVariable Integer userId) {
        List<CommentDto> commentList = commentService.listCommentByUser(userId);

        if (commentList == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentList);
    }

    //TODO testar
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        User user = authService.getActiveUser();
        Comment comment = commentService.getComment(id);

        if (comment == null) {
            throw new ClonnitException("Comentário não encontrado");
        } else if (comment.getUser() != user) {
            throw new ClonnitException("Não é permitido alterar o comentário de outro usuário");
        }

        commentService.deleteComment(comment);
        return ResponseEntity.status(HttpStatus.OK).body("Comentário " + id + "removido com sucesso");
    }
}
