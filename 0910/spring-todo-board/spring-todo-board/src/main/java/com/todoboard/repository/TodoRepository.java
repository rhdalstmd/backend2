package com.todoboard.repository;

import com.todoboard.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * ========================================
 * To-Do Repository 인터페이스
 * ========================================
 * - To-Do 엔티티에 대한 데이터베이스 접근 계층
 * - JpaRepository 상속으로 기본 CRUD 메서드 제공
 * - 커스텀 검색 메서드 정의
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    /**
     * 제목으로 검색 (페이징)
     * @param title 검색할 제목 (부분 일치)
     * @param pageable 페이징 정보
     * @return 검색 결과 페이지
     */
    Page<Todo> findByTitleContaining(String title, Pageable pageable);

    /**
     * 내용으로 검색 (페이징)
     * @param content 검색할 내용 (부분 일치)
     * @param pageable 페이징 정보
     * @return 검색 결과 페이지
     */
    Page<Todo> findByContentContaining(String content, Pageable pageable);

    /**
     * 작성자로 검색 (페이징)
     * @param author 검색할 작성자명
     * @param pageable 페이징 정보
     * @return 검색 결과 페이지
     */
    Page<Todo> findByAuthorContaining(String author, Pageable pageable);

    /**
     * 완료 여부로 검색 (페이징)
     * @param completed 완료 여부
     * @param pageable 페이징 정보
     * @return 검색 결과 페이지
     */
    Page<Todo> findByCompleted(Boolean completed, Pageable pageable);

    /**
     * 제목 또는 내용으로 검색 (페이징)
     * @param title 검색할 제목
     * @param content 검색할 내용
     * @param pageable 페이징 정보
     * @return 검색 결과 페이지
     */
    @Query("SELECT t FROM Todo t WHERE t.title LIKE %:keyword% OR t.content LIKE %:keyword%")
    Page<Todo> searchByTitleOrContent(@Param("keyword") String keyword, Pageable pageable);
}
