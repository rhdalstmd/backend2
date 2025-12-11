package com.todoboard.entity;

import javax.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * ========================================
 * 공통 엔티티 추상 클래스
 * ========================================
 * - 모든 엔티티가 공통으로 가지는 필드 정의
 * - 생성일, 수정일 자동 관리
 */
@Getter
@MappedSuperclass // 상속받는 클래스에 매핑 정보만 제공
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 리스너 등록
public abstract class BaseEntity {

    /**
     * 생성 일시 (자동 설정)
     */
    @CreatedDate
    @Column(updatable = false) // 수정 불가
    private LocalDateTime createdAt;

    /**
     * 수정 일시 (자동 업데이트)
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
