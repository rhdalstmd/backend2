package com.todoboard.controller;

import com.todoboard.dto.CommentRequest;
import com.todoboard.service.CommentService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ========================================
 * 댓글 컨트롤러
 * ========================================
 * - 댓글 관련 HTTP 요청 처리
 */
@Controller
@RequestMapping("/todos/{todoId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * ========================================
     * 댓글 작성
     * ========================================
     */
    @PostMapping
    public String create(
            @PathVariable Long todoId,
            @Valid @ModelAttribute CommentRequest commentRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        // 검증 실패 시 에러 메시지와 함께 상세 페이지로 돌아가기
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentError", "댓글 내용과 작성자를 입력해주세요.");
            return "redirect:/todos/" + todoId;
        }
        
        // 댓글 생성
        commentService.createComment(todoId, commentRequest);
        
        // 성공 메시지 전달
        redirectAttributes.addFlashAttribute("message", "댓글이 작성되었습니다.");
        
        // 상세 페이지로 리다이렉트
        return "redirect:/todos/" + todoId;
    }

    /**
     * ========================================
     * 댓글 삭제
     * ========================================
     */
    @PostMapping("/{commentId}/delete")
    public String delete(
            @PathVariable Long todoId,
            @PathVariable Long commentId,
            RedirectAttributes redirectAttributes
    ) {
        // 댓글 삭제
        commentService.deleteComment(commentId);
        
        // 성공 메시지 전달
        redirectAttributes.addFlashAttribute("message", "댓글이 삭제되었습니다.");
        
        // 상세 페이지로 리다이렉트
        return "redirect:/todos/" + todoId;
    }
}
