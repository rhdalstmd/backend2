package com.todoboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * ========================================
 * To-Do 게시판 애플리케이션 메인 클래스
 * ========================================
 * - 스프링 부트 애플리케이션의 진입점
 * - JPA Auditing 기능 활성화 (생성일, 수정일 자동 관리)
 */
@SpringBootApplication
@EnableJpaAuditing // JPA Auditing 활성화
public class TodoBoardApplication {

    /**
     * 애플리케이션 시작 메서드
     * @param args 커맨드 라인 인자
     */
    public static void main(String[] args) {
        SpringApplication.run(TodoBoardApplication.class, args);
    }
}
