package com.todoboard.dto;

import com.todoboard.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ========================================
 * To-Do 응답 DTO
 * ========================================
 * - 클라이언트에게 전달하는 To-Do 데이터
 * - Entity를 DTO로 변환하여 필요한 정보만 노출
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponse {

    /**
     * 게시글 ID
     */
    private Long id;

    /**
     * 제목
     */
    private String title;

    /**
     * 내용
     */
    private String content;

    /**
     * 완료 여부
     */
    private Boolean completed;

    /**
     * 작성자
     */
    private String author;

    /**
     * 조회수
     */
    private Long viewCount;

    /**
     * 댓글 개수
     */
    private Integer commentCount;

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
     * @param todo Todo 엔티티
     * @return TodoResponse DTO
     */
    public static TodoResponse from(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .content(todo.getContent())
                .completed(todo.getCompleted())
                .author(todo.getAuthor())
                .viewCount(todo.getViewCount())
                .commentCount(todo.getComments().size()) // 댓글 개수 계산
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }
}
