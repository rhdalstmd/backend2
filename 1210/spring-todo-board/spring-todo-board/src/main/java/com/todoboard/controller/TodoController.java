package com.todoboard.controller;

import com.todoboard.dto.TodoRequest;
import com.todoboard.dto.TodoResponse;
import com.todoboard.entity.Todo;
import com.todoboard.service.TodoService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ========================================
 * To-Do 컨트롤러
 * ========================================
 * - To-Do 관련 HTTP 요청 처리
 * - Thymeleaf 뷰 렌더링
 */
@Controller
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * ========================================
     * 목록 페이지 (페이징, 검색, 필터링)
     * ========================================
     */
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page, // 페이지 번호 (0부터 시작)
            @RequestParam(defaultValue = "10") int size, // 페이지 크기
            @RequestParam(required = false) String keyword, // 검색 키워드
            @RequestParam(required = false) Boolean completed, // 완료 여부 필터
            Model model
    ) {
        // 페이징 정보 생성 (최신순 정렬)
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        Page<TodoResponse> todoPage;
        
        // 완료 여부 필터가 있으면 필터링
        if (completed != null) {
            todoPage = todoService.getTodosByCompleted(completed, pageable);
        }
        // 검색 키워드가 있으면 검색
        else if (keyword != null && !keyword.trim().isEmpty()) {
            todoPage = todoService.searchTodos(keyword, pageable);
        }
        // 전체 목록 조회
        else {
            todoPage = todoService.getAllTodos(pageable);
        }
        
        // 뷰에 데이터 전달
        model.addAttribute("todoPage", todoPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("completed", completed);
        
        return "todos/list"; // templates/todos/list.html
    }

    /**
     * ========================================
     * 상세 페이지
     * ========================================
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Todo todo = todoService.getTodoById(id);
        model.addAttribute("todo", todo);
        return "todos/detail"; // templates/todos/detail.html
    }

    /**
     * ========================================
     * 작성 폼 페이지
     * ========================================
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("todoRequest", new TodoRequest());
        return "todos/form"; // templates/todos/form.html
    }

    /**
     * ========================================
     * 작성 처리
     * ========================================
     */
    @PostMapping
    public String create(
            @Valid @ModelAttribute TodoRequest todoRequest, // 입력 검증
            BindingResult bindingResult, // 검증 결과
            RedirectAttributes redirectAttributes
    ) {
        // 검증 실패 시 폼으로 돌아가기
        if (bindingResult.hasErrors()) {
            return "todos/form";
        }
        
        // Todo 생성
        TodoResponse created = todoService.createTodo(todoRequest);
        
        // 성공 메시지 전달
        redirectAttributes.addFlashAttribute("message", "게시글이 작성되었습니다.");
        
        // 상세 페이지로 리다이렉트
        return "redirect:/todos/" + created.getId();
    }

    /**
     * ========================================
     * 수정 폼 페이지
     * ========================================
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Todo todo = todoService.getTodoById(id);
        
        // Todo를 TodoRequest로 변환
        TodoRequest todoRequest = TodoRequest.builder()
                .title(todo.getTitle())
                .content(todo.getContent())
                .author(todo.getAuthor())
                .build();
        
        model.addAttribute("todoRequest", todoRequest);
        model.addAttribute("todoId", id);
        model.addAttribute("isEdit", true); // 수정 모드 플래그
        
        return "todos/form";
    }

    /**
     * ========================================
     * 수정 처리
     * ========================================
     */
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute TodoRequest todoRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        // 검증 실패 시 폼으로 돌아가기
        if (bindingResult.hasErrors()) {
            model.addAttribute("todoId", id);
            model.addAttribute("isEdit", true);
            return "todos/form";
        }
        
        // Todo 수정
        todoService.updateTodo(id, todoRequest);
        
        // 성공 메시지 전달
        redirectAttributes.addFlashAttribute("message", "게시글이 수정되었습니다.");
        
        // 상세 페이지로 리다이렉트
        return "redirect:/todos/" + id;
    }

    /**
     * ========================================
     * 삭제 처리
     * ========================================
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        todoService.deleteTodo(id);
        
        // 성공 메시지 전달
        redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
        
        // 목록 페이지로 리다이렉트
        return "redirect:/todos";
    }

    /**
     * ========================================
     * 완료 상태 토글
     * ========================================
     */
    @PostMapping("/{id}/toggle")
    public String toggleCompleted(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        TodoResponse updated = todoService.toggleCompleted(id);
        
        // 성공 메시지 전달
        String status = updated.getCompleted() ? "완료" : "미완료";
        redirectAttributes.addFlashAttribute("message", "상태가 " + status + "로 변경되었습니다.");
        
        // 상세 페이지로 리다이렉트
        return "redirect:/todos/" + id;
    }
}
