package com.algaworks.comment_service.domain.model;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class CommentId {

    private TSID value;

    public CommentId(TSID value) {
        this.value = value;
    }

    public CommentId(Long value) {
        this.value = TSID.from(value);
    }

    public CommentId(String value) {
        this.value = TSID.from(value);
    }
}
