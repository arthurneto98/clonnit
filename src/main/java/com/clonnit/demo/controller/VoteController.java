package com.clonnit.demo.controller;

import com.clonnit.demo.dto.VoteDto;
import com.clonnit.demo.exceptions.ClonnitException;
import com.clonnit.demo.model.User;
import com.clonnit.demo.service.AuthService;
import com.clonnit.demo.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vote")
@AllArgsConstructor
@Slf4j
public class VoteController {
    private final VoteService voteService;
    private final AuthService authService;

    //TODO Create or update?
    @PostMapping
    public ResponseEntity<VoteDto> create(@RequestBody VoteDto vote) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(voteService.saveVote(vote));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<VoteDto> getByPost(@PathVariable Integer postId) {
        User user = authService.getActiveUser();
        if (user == null) {
            throw new ClonnitException("Não há usuário autenticado");
        }

        VoteDto dto = voteService.getVoteByPost(postId, user.getId());
        return dto != null ?
                ResponseEntity.status(HttpStatus.OK).body(dto) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<VoteDto> getByComment(@PathVariable Integer commentId) {
        User user = authService.getActiveUser();
        if (user == null) {
            throw new ClonnitException("Não há usuário autenticado");
        }

        VoteDto dto = voteService.getVoteByComment(commentId, user.getId());
        return dto != null ?
                ResponseEntity.status(HttpStatus.OK).body(dto) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    //TODO full implementar e testar
    //TODO Delete
}
