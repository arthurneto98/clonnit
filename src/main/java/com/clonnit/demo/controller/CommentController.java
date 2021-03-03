package com.clonnit.demo.controller;

import com.clonnit.demo.dto.CommentDto;
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

    @PostMapping
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto comment) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.saveComment(comment));
    }

    @GetMapping("/post/{id}")
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

    @GetMapping("/user/{id}")
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
}
