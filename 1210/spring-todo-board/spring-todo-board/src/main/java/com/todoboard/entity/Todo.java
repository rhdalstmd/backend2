package com.todoboard.entity;

import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ========================================
 * To-Do 게시글 엔티티
 * ========================================
 * - To-Do 게시글의 정보를 저장하는 엔티티
 * - 제목, 내용, 완료 여부, 작성자 정보 포함
 * - 댓글과 1:N 관계
 */
@Entity
@Table(name = "todos") // 테이블 이름 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 (JPA 요구사항)
@AllArgsConstructor
@Builder
public class Todo extends BaseEntity {

    /**
     * 게시글 ID (Primary Key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    /**
     * 게시글 제목 (필수, 최대 200자)
     */
    @Column(nullable = false, length = 200)
    private String title;

    /**
     * 게시글 내용 (필수, TEXT 타입)
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * 완료 여부 (기본값: false)
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean completed = false;

    /**
     * 작성자 이름 (필수, 최대 50자)
     */
    @Column(nullable = false, length = 50)
    private String author;

    /**
     * 조회수 (기본값: 0)
     */
    @Column(nullable = false)
    @Builder.Default
    private Long viewCount = 0L;

    /**
     * 댓글 목록 (1:N 관계)
     * - CascadeType.ALL: Todo 삭제 시 댓글도 함께 삭제
     * - orphanRemoval: 고아 객체 자동 제거
     */
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    /**
     * ========================================
     * 비즈니스 메서드
     * ========================================
     */

    /**
     * 게시글 수정
     * @param title 새 제목
     * @param content 새 내용
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * 완료 상태 토글
     */
    public void toggleCompleted() {
        this.completed = !this.completed;
    }

    /**
     * 조회수 증가
     */
    public void increaseViewCount() {
        this.viewCount++;
    }

    /**
     * 댓글 추가
     * @param comment 추가할 댓글
     */
    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setTodo(this); // 양방향 관계 설정
    }
}
