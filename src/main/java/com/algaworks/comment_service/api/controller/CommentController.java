package com.algaworks.comment_service.api.controller;

import com.algaworks.comment_service.api.client.CommentModerationClient;
import com.algaworks.comment_service.api.model.CommentInput;
import com.algaworks.comment_service.api.model.CommentOutput;
import com.algaworks.comment_service.api.model.ModerationInput;
import com.algaworks.comment_service.api.model.ModerationOutput;
import com.algaworks.comment_service.common.IdGenerator;
import com.algaworks.comment_service.domain.model.Comment;
import com.algaworks.comment_service.domain.model.CommentId;
import com.algaworks.comment_service.domain.repository.CommentRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentModerationClient commentModerationClient;

    @GetMapping
    public Page<CommentOutput> getComments(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(this::toOutput);
    }

    @GetMapping("/{id}")
    public CommentOutput getSingleComment(@PathVariable TSID id) {
        Comment comment = commentRepository.findById(new CommentId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return toOutput(comment);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutput createComment(@RequestBody CommentInput input) {
        Comment comment = Comment.builder()
                .id(new CommentId(IdGenerator.generateId()))
                .author(input.getAuthor())
                .text(input.getText())
                .createdAt(OffsetDateTime.now())
                .build();

        ModerationInput build = ModerationInput.builder()
                .commentId(comment.getId().getValue().toString())
                .text(comment.getText())
                .build();

        ModerationOutput moderation = commentModerationClient.moderate(build);

        if (!moderation.getApproved()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT, moderation.getReason());
        }

        return toOutput(commentRepository.saveAndFlush(comment));
    }

    private CommentOutput toOutput(Comment comment) {
        return CommentOutput.builder()
                .id(comment.getId().getValue())
                .author(comment.getAuthor())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
