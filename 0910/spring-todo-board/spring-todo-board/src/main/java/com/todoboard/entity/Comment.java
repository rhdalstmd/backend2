package com.todoboard.entity;

import javax.persistence.*;
import lombok.*;

/**
 * ========================================
 * 댓글 엔티티
 * ========================================
 * - To-Do 게시글에 달린 댓글 정보를 저장
 * - Todo와 N:1 관계
 */
@Entity
@Table(name = "comments") // 테이블 이름 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 (JPA 요구사항)
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity {

    /**
     * 댓글 ID (Primary Key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    /**
     * 댓글 내용 (필수, 최대 500자)
     */
    @Column(nullable = false, length = 500)
    private String content;

    /**
     * 작성자 이름 (필수, 최대 50자)
     */
    @Column(nullable = false, length = 50)
    private String author;

    /**
     * 연관된 Todo (N:1 관계)
     * - FetchType.LAZY: 지연 로딩 (필요할 때만 조회)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false) // 외래키 컬럼명
    @Setter // Todo와의 양방향 관계 설정을 위해 Setter 추가
    private Todo todo;

    /**
     * ========================================
     * 비즈니스 메서드
     * ========================================
     */

    /**
     * 댓글 내용 수정
     * @param content 새 내용
     */
    public void update(String content) {
        this.content = content;
    }
}
