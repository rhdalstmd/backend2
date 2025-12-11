package com.todoboard.service;

import com.todoboard.dto.CommentRequest;
import com.todoboard.dto.CommentResponse;
import com.todoboard.entity.Comment;
import com.todoboard.entity.Todo;
import com.todoboard.repository.CommentRepository;
import com.todoboard.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ========================================
 * 댓글 서비스 클래스
 * ========================================
 * - 댓글 관련 비즈니스 로직 처리
 * - 트랜잭션 관리
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    /**
     * ========================================
     * 특정 Todo의 댓글 목록 조회
     * ========================================
     */
    public List<CommentResponse> getCommentsByTodoId(Long todoId) {
        return commentRepository.findByTodoIdOrderByCreatedAtAsc(todoId)
                .stream()
                .map(CommentResponse::from) // Entity를 DTO로 변환
                .collect(Collectors.toList());
    }

    /**
     * ========================================
     * 댓글 생성
     * ========================================
     */
    @Transactional
    public CommentResponse createComment(Long todoId, CommentRequest request) {
        // 존재하는 Todo 조회
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. ID: " + todoId));
        
        // DTO를 Entity로 변환
        Comment comment = Comment.builder()
                .content(request.getContent())
                .author(request.getAuthor())
                .build();
        
        // Todo와 Comment 연관관계 설정
        todo.addComment(comment);
        
        // 저장
        Comment savedComment = commentRepository.save(comment);
        
        return CommentResponse.from(savedComment);
    }

    /**
     * ========================================
     * 댓글 수정
     * ========================================
     */
    @Transactional
    public CommentResponse updateComment(Long commentId, CommentRequest request) {
        // 존재하는 Comment 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. ID: " + commentId));
        
        // 변경 감지를 통한 업데이트
        comment.update(request.getContent());
        
        return CommentResponse.from(comment);
    }

    /**
     * ========================================
     * 댓글 삭제
     * ========================================
     */
    @Transactional
    public void deleteComment(Long commentId) {
        // 존재하는 Comment 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. ID: " + commentId));
        
        // 삭제
        commentRepository.delete(comment);
    }
}
