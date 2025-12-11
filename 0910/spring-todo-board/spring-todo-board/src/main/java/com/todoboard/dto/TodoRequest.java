package com.todoboard.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ========================================
 * To-Do 생성/수정 요청 DTO
 * ========================================
 * - 클라이언트로부터 받는 To-Do 데이터
 * - 입력 검증 어노테이션 포함
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRequest {

    /**
     * 제목 (필수, 1~200자)
     */
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 200, message = "제목은 200자 이내로 입력해주세요.")
    private String title;

    /**
     * 내용 (필수)
     */
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    /**
     * 작성자 (필수, 1~50자)
     */
    @NotBlank(message = "작성자를 입력해주세요.")
    @Size(max = 50, message = "작성자는 50자 이내로 입력해주세요.")
    private String author;
}
