package com.algaworks.comment_service.api.model;

import lombok.Data;

@Data
public class ModerationOutput {

    private Boolean approved;
    private String reason;
}
