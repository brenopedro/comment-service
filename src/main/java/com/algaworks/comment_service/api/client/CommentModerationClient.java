package com.algaworks.comment_service.api.client;

import com.algaworks.comment_service.api.model.ModerationInput;
import com.algaworks.comment_service.api.model.ModerationOutput;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/moderate")
public interface CommentModerationClient {

    @PostExchange
    ModerationOutput moderate(@RequestBody ModerationInput input);
}
