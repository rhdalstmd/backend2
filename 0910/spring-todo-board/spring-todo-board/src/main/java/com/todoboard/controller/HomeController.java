package com.todoboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ========================================
 * 홈 컨트롤러
 * ========================================
 * - 루트 경로 접근 시 Todo 목록으로 리다이렉트
 */
@Controller
public class HomeController {

    /**
     * 루트 경로 접근 시 Todo 목록으로 리다이렉트
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/todos";
    }
}
