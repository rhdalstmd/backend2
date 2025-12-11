package com.todoboard.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ========================================
 * 댓글 생성/수정 요청 DTO
 * ========================================
 * - 클라이언트로부터 받는 댓글 데이터
 * - 입력 검증 어노테이션 포함
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {

    /**
     * 댓글 내용 (필수, 1~500자)
     */
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @Size(max = 500, message = "댓글은 500자 이내로 입력해주세요.")
    private String content;

    /**
     * 작성자 (필수, 1~50자)
     */
    @NotBlank(message = "작성자를 입력해주세요.")
    @Size(max = 50, message = "작성자는 50자 이내로 입력해주세요.")
    private String author;
}
