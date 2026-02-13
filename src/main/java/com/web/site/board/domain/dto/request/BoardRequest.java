package com.web.site.board.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "게시판 항목")
public record BoardRequest(

        @Schema(description = "게시판 Id")
        Long boardId,

        @Schema(description = "제목")
        @NotBlank(message = "제목은 필수 입니다.")
        String title,

        @Schema(description = "내용")
        @NotBlank(message = "내용은 필수 입니다.")
        String content
) {}
