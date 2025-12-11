package com.todoboard.dto;

import com.todoboard.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ========================================
 * 댓글 응답 DTO
 * ========================================
 * - 클라이언트에게 전달하는 댓글 데이터
 * - Entity를 DTO로 변환하여 필요한 정보만 노출
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    /**
     * 댓글 ID
     */
    private Long id;

    /**
     * 댓글 내용
     */
    private String content;

    /**
     * 작성자
     */
    private String author;

    /**
     * 생성 일시
     */
    private LocalDateTime createdAt;

    /**
     * 수정 일시
     */
    private LocalDateTime updatedAt;

    /**
     * Entity를 DTO로 변환하는 정적 팩토리 메서드
     * @param comment Comment 엔티티
     * @return CommentResponse DTO
     */
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
