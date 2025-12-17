package com.algaworks.comment_service.api.controller;

import com.algaworks.comment_service.api.model.CommentInput;
import com.algaworks.comment_service.api.model.CommentOutput;
import com.algaworks.comment_service.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @GetMapping
    public Page<CommentOutput> getComments(Pageable pageable) {
        return Page.empty();
    }

    @GetMapping("/{id}")
    public CommentOutput getSingleComment(@PathVariable TSID id) {
        return CommentOutput.builder()
                .author("TESTE").build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutput createComment(@RequestBody CommentInput comment) {
        return toOutput(comment);
    }

    private CommentOutput toOutput(CommentInput comment) {
        return CommentOutput.builder()
                .id(IdGenerator.generateId())
                .author(comment.getAuthor())
                .text(comment.getText())
                .createdAt(OffsetDateTime.now())
                .build();
    }
}
