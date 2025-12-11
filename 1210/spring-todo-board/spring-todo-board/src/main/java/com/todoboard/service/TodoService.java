package com.todoboard.service;

import com.todoboard.dto.TodoRequest;
import com.todoboard.dto.TodoResponse;
import com.todoboard.entity.Todo;
import com.todoboard.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ========================================
 * To-Do 서비스 클래스
 * ========================================
 * - To-Do 관련 비즈니스 로직 처리
 * - 트랜잭션 관리
 */
@Service
@RequiredArgsConstructor // final 필드 생성자 자동 생성
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션
public class TodoService {

    private final TodoRepository todoRepository;

    /**
     * ========================================
     * 전체 목록 조회 (페이징)
     * ========================================
     */
    public Page<TodoResponse> getAllTodos(Pageable pageable) {
        return todoRepository.findAll(pageable)
                .map(TodoResponse::from); // Entity를 DTO로 변환
    }

    /**
     * ========================================
     * 검색 (제목 또는 내용, 페이징)
     * ========================================
     */
    public Page<TodoResponse> searchTodos(String keyword, Pageable pageable) {
        // 키워드가 없으면 전체 조회
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllTodos(pageable);
        }
        
        return todoRepository.searchByTitleOrContent(keyword.trim(), pageable)
                .map(TodoResponse::from);
    }

    /**
     * ========================================
     * 완료 여부로 필터링 (페이징)
     * ========================================
     */
    public Page<TodoResponse> getTodosByCompleted(Boolean completed, Pageable pageable) {
        return todoRepository.findByCompleted(completed, pageable)
                .map(TodoResponse::from);
    }

    /**
     * ========================================
     * 상세 조회 (조회수 증가)
     * ========================================
     */
    @Transactional // 쓰기 작업이므로 readOnly = false
    public Todo getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. ID: " + id));
        
        // 조회수 증가
        todo.increaseViewCount();
        
        // 댓글 목록을 즉시 로딩 (Lazy Loading 방지)
        todo.getComments().size();
        
        return todo;
    }

    /**
     * ========================================
     * 생성
     * ========================================
     */
    @Transactional
    public TodoResponse createTodo(TodoRequest request) {
        // DTO를 Entity로 변환
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getAuthor())
                .build();
        
        // 저장
        Todo savedTodo = todoRepository.save(todo);
        
        return TodoResponse.from(savedTodo);
    }

    /**
     * ========================================
     * 수정
     * ========================================
     */
    @Transactional
    public TodoResponse updateTodo(Long id, TodoRequest request) {
        // 존재하는 Todo 조회
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. ID: " + id));
        
        // 변경 감지 (Dirty Checking)를 통한 업데이트
        todo.update(request.getTitle(), request.getContent());
        
        return TodoResponse.from(todo);
    }

    /**
     * ========================================
     * 삭제
     * ========================================
     */
    @Transactional
    public void deleteTodo(Long id) {
        // 존재하는 Todo 조회
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. ID: " + id));
        
        // 삭제 (연관된 댓글도 함께 삭제됨 - CascadeType.ALL)
        todoRepository.delete(todo);
    }

    /**
     * ========================================
     * 완료 상태 토글
     * ========================================
     */
    @Transactional
    public TodoResponse toggleCompleted(Long id) {
        // 존재하는 Todo 조회
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. ID: " + id));
        
        // 완료 상태 변경
        todo.toggleCompleted();
        
        return TodoResponse.from(todo);
    }
}
