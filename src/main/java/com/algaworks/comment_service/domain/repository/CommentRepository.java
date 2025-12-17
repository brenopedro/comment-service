package com.algaworks.comment_service.domain.repository;

import com.algaworks.comment_service.domain.model.Comment;
import com.algaworks.comment_service.domain.model.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, CommentId> {
}
