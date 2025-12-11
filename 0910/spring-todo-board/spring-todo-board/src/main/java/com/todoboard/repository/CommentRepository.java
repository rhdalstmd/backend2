package com.todoboard.repository;

import com.todoboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ========================================
 * 댓글 Repository 인터페이스
 * ========================================
 * - Comment 엔티티에 대한 데이터베이스 접근 계층
 * - JpaRepository 상속으로 기본 CRUD 메서드 제공
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 Todo의 모든 댓글 조회
     * @param todoId Todo ID
     * @return 댓글 목록 (생성일 기준 오름차순)
     */
    List<Comment> findByTodoIdOrderByCreatedAtAsc(Long todoId);

    /**
     * 특정 Todo의 댓글 개수 조회
     * @param todoId Todo ID
     * @return 댓글 개수
     */
    Long countByTodoId(Long todoId);
}
