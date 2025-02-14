package com.clonnit.demo.controller;

import com.clonnit.demo.dto.VoteDto;
import com.clonnit.demo.exceptions.ClonnitException;
import com.clonnit.demo.model.User;
import com.clonnit.demo.model.Vote;
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

    @PostMapping
    public ResponseEntity<VoteDto> create(@RequestBody VoteDto vote) {
        User user = authService.getActiveUser();
        Boolean isUpdate = vote.getId() != null;

        if (isUpdate && !vote.getUserId().equals(user.getId())) {
            throw new ClonnitException("Não é permitido editar o voto de outro usuário");
        }


        HttpStatus status = isUpdate ? HttpStatus.OK : HttpStatus.CREATED;
        return ResponseEntity
                .status(status)
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

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        User user = authService.getActiveUser();
        Vote vote = voteService.getVote(id);

        if (vote == null) {
            throw new ClonnitException("Voto não encontrado");
        } else if (vote.getUser() != user) {
            throw new ClonnitException("Não é permitido alterar o voto de outro usuário");
        }

        voteService.deleteVote(vote);
        return ResponseEntity.status(HttpStatus.OK).body("Voto" + id + " removido com sucesso");
    }
}
